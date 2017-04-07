package com.Raiti.SomeEatingItems;

import java.util.Random;

import com.Raiti.SomeEatingItems.Scheduler.SchedulerTask;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.Raiti.SomeEatingItems.Packet.EatingItemStartMessage;
import com.Raiti.SomeEatingItems.Packet.EatingItemStopMessage;
import com.Raiti.SomeEatingItems.Packet.PacketHander;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * ForgeEventFactory's EVENT_BUS on hook.
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ForgeEventHook {
	
	/**
	 * Random.
	 */
	private Random random = new Random();
	
	
	/**
	 * This event occurs when player was right click.
	 *
	 * @param event RightClick Event
	 */
	@SubscribeEvent
	public void onRightClickItem (PlayerInteractEvent.RightClickItem event) {
		if (!event.getWorld().isRemote) return; //サーバー側の処理はさせないっ!!
		if (FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.getItemStack().getTagCompound()) == null)
			return; //タグが無かったら(null)無視
		event.getEntityLiving().setActiveHand(event.getHand()); //アイテムを使用している状態へ
		PacketHander.INSTANCE.sendToServer(new EatingItemStartMessage()); // サーバーへ通知
		event.setCanceled(true);
		event.getEntityLiving().sendMessage(new TextComponentString("RightClick!"));
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
		int eatingTime = FoodMetaDataStructure.getEatingTime(compound); //食べる時間を取得
		event.setDuration((eatingTime <= 0 ? 32 : eatingTime) + 27); //かかる時間を設定(+27はバニラでの処理を避けるため)
		
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
		
		event.setDuration(event.getDuration() + 1); //アイテムの使用時間を止める
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
		if (event.getEntityLiving().world.isRemote) PacketHander.INSTANCE.sendToServer(new EatingItemStopMessage());
	}
	
	/**
	 * This method occurs finished item use.
	 *
	 * @param entity player.
	 * @param stack  used item.
	 */
	private void onPlayerUseItem_Finish (EntityLivingBase entity, ItemStack stack) {
		int count = stack.getCount();
		updateItemUse(entity, stack, 16);
		stack.shrink(1);
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, entity.world.rand.nextFloat() * 0.1F + 0.9F);
		onEaten(stack);
		entity.resetActiveHand();
		entity.setHeldItem(entity.getActiveHand(), stack);
		//統計情報を追加するかも？
		
	}
	
	/**
	 * Update to Sound and Particle.
	 *
	 * @param entity              player.
	 * @param stack               item.
	 * @param eatingParticleCount particle count.
	 */
	@SuppressWarnings("SameParameterValue")
	private void updateItemUse (EntityLivingBase entity, ItemStack stack, int eatingParticleCount) {
		for (int i = 0; i < eatingParticleCount; i++) { //パーティクルの計算
			Vec3d vec3d = new Vec3d(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec3d = vec3d.rotatePitch(-entity.rotationPitch * 0.017453292F);
			vec3d = vec3d.rotateYaw(-entity.rotationYaw * 0.017453292F);
			double d0 = (double) (-this.random.nextFloat()) * 0.6D - 0.3D;
			Vec3d vec3d1 = new Vec3d(((double) this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
			vec3d1 = vec3d1.rotatePitch(-entity.rotationPitch * 0.017453292F);
			vec3d1 = vec3d1.rotateYaw(-entity.rotationYaw * 0.017453292F);
			vec3d1 = vec3d1.addVector(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
			
			if (stack.getHasSubtypes()) //アイテムがメタデータを持っている場合
				entity.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord,
						Item.getIdFromItem(stack.getItem()), stack.getMetadata()); //パーティクルの発生
			else //無い場合
				entity.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord,
						Item.getIdFromItem(stack.getItem())); //パーティクルの発生
		}
		
		entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
		//音を鳴らす
	}
	
	/**
	 * On eat.<br>
	 * Add a effect to player.
	 *
	 * @param stack eat item.
	 */
	private void onEaten (ItemStack stack) {
		
	}
}
