package com.Raiti.SomeEatingItems;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.util.EnumHand;

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.Raiti.SomeEatingItems.Packet.EatingItemStartMessage;
import com.Raiti.SomeEatingItems.Packet.EatingItemStopMessage;
import com.Raiti.SomeEatingItems.Packet.PacketHander;

/**
 * ForgeEventFactory's EVENT_BUS on hook.
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Cancelable
public class ForgeEventHook {
	
	/**
	 * Random.
	 */
	private Random random = new Random();
	
	
	@SubscribeEvent
	public void blockPlaceEvent (BlockEvent.PlaceEvent event) {
		EntityPlayer player = event.getPlayer();
		ItemStack stack = player.getHeldItem(event.getHand());
		if (FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(stack.getTagCompound()) != null) {
			event.setCanceled(true);
			((EntityPlayerMP)player).connection.sendPacket(new SPacketHeldItemChange(player.inventory.currentItem));
			
		}
		
	}
	
	@SubscribeEvent
	public void onRightClickEntity (PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() == EnumHand.MAIN_HAND) {
			event.setCanceled(FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItemStack().getTagCompound()) != null);
		} else {
			event.setCanceled(true);
		}
	}
	
	/**
	 * This event occurs when player was right click.
	 *
	 * @param event RightClick Event
	 */
	@SubscribeEvent
	public void onRightClickItem (PlayerInteractEvent.RightClickItem event) {
		if (!event.getWorld().isRemote) {
			
			if (event.getHand() == EnumHand.OFF_HAND) {
				NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND).getTagCompound());
				if (compound != null && event.getEntityPlayer().canEat(FoodMetaDataStructure.canAlwaysEaten(compound)))
					event.setCanceled(true);
			}
			
			if (FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItemStack().getTagCompound()) != null)
				event.setCanceled(true);
			return; //サーバー側の処理はさせないっ!!
		}
		if (event.getHand() == EnumHand.OFF_HAND && event.getEntityLiving().isHandActive()) {
			event.setCanceled(true);
			return; //メインハンドがアクティブだった場合処理させない
		}
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItemStack().getTagCompound());
		if (compound == null) return; //タグが無かったら(null)無視
		event.setCanceled(true);
		if (!event.getEntityPlayer().canEat(FoodMetaDataStructure.canAlwaysEaten(compound))) return; //満腹の時食べられないように
		
		//event.getEntityLiving().sendMessage(new TextComponentString("[Client]H[" + event.getHand().name() + "]RightClick-" + event.getItemStack()));//debug
		event.getEntityLiving().setActiveHand(event.getHand()); //アイテムを使用している状態へ
		PacketHander.INSTANCE.sendToServer(new EatingItemStartMessage(event.getHand())); // サーバーへ通知
	}
	
	/**
	 * This event occurs when player starting item use.
	 *
	 * @param event UseItemStart event.This event occurs every second at player using item.
	 */
	@SubscribeEvent
	public void onPlayerUseItem_Start (LivingEntityUseItemEvent.Start event) {
		if (!event.getEntityLiving().getEntityWorld().isRemote) return; //サーバー側の処理はさせないっ!!
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItem().getTagCompound()); //タグの取得
		if (compound == null) return; //タグが無かったら(null)無視
		event.setDuration(59); //アイテムアニメーションの時間
		
	}
	
	/**
	 * This event occurs every tick at player using item.
	 *
	 * @param event UseItemTick event.
	 */
	@SubscribeEvent
	public void onPlayerUseItem_Tick (LivingEntityUseItemEvent.Tick event) {
		if (!event.getEntityLiving().getEntityWorld().isRemote) return; //サーバー側での処理はさせないっ!!
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItem().getTagCompound());    //タグの取得
		if (compound == null) return; //タグが無かったら(null)無視
		
		//final int eatingTime = FoodMetaDataStructure.getEatingTime(compound); //食べるのにかかる時間を取得
		//final int duration = event.getDuration(); //残り時間を取得
		if (event.getDuration() <= 27) event.setDuration(51);//アイテムの使用時間の
		/*
		if (duration <= 27) { //Durationが27以下だった場合Finishの処理
			onPlayerUseItem_Finish(event.getEntityLiving(), event.getItem());
			event.setDuration(0);
		} else if (event.getDuration() <= 52 && event.getDuration() % 4 == 0)
			updateItemUse(event.getEntityLiving(), event.getItem(), 5);
		*/
	}
	
	/**
	 * This event occurs at player stopped use item.
	 *
	 * @param event UseItemStop event.
	 */
	@SubscribeEvent
	public void onPlayerUseItem_Stop (LivingEntityUseItemEvent.Stop event) {
		if (event.getEntityLiving().world.isRemote && FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItem().getTagCompound()) != null) {
			//event.getEntityLiving().sendMessage(new TextComponentString("[Client]H[" + event.getEntityLiving().getActiveHand().name() + "]EatingStop-" + event.getItem()));//debug
			PacketHander.INSTANCE.sendToServer(new EatingItemStopMessage(event.getEntityLiving().getActiveHand()));
		}
	}
	
	
}
