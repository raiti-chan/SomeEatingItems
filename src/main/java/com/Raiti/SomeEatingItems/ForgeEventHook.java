package com.Raiti.SomeEatingItems;

import java.util.Random;

import com.Raiti.SomeEatingItems.Packet.PacketHander;
import com.Raiti.SomeEatingItems.Packet.RightClickItemMessage;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

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
	 * Player Hands SlotIndex<br>
	 * Client only.
	 */
	private int currentPlayerItem = -1;
	
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
	public void onRightClickItem (PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
			return;                                        //右クリックじゃない場合無視
		EntityPlayer player = event.entityPlayer;                                                                        //プレイヤーの取得
		ItemStack heldItem = player.getHeldItem();                                                                        //手に持っているアイテムの取得
		if (heldItem == null)
			return;                                                                                    //持っていなかったら(null)無視
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(heldItem.getTagCompound());    //タグの取得
		if (compound == null)
			return;                                                                                    //タグが無かったら(null)無視
		//player.addChatComponentMessage(new ChatComponentTranslation((player.worldObj.isRemote ? "[Client]" : "[Server]") + "ItemClick"));
		
		int eatingTime = compound.getInteger("EatingTime");                                                    //食べる時間を取得
		player.setItemInUse(heldItem, (eatingTime <= 0 ? 32 : eatingTime) + 26);                            //プレイヤーに食べる時間とアイテムをセット(26はバニラでupdateItemUseが起きないように)
		
		
		if (player.worldObj.isRemote) {                //クライアント側の処理
			NetHandlerPlayClient playClient = (NetHandlerPlayClient) FMLClientHandler.instance().getClientPlayHandler();//ネットハンドラの取得
			int index = player.inventory.currentItem;
			if (this.currentPlayerItem != index) {
				this.currentPlayerItem = index;
				playClient.addToSendQueue(new C09PacketHeldItemChange(player.inventory.currentItem));                    //もしスロットが変わっていたらサーバーに通知
			}
			PacketHander.INSTANCE.sendToServer(new RightClickItemMessage());                                            //サーバーにRightClickItemイベントが発火されるように通知
			
		} else {                                    //サーバー側の処理
			if (heldItem.getItem().getItemUseAction(heldItem) != EnumAction.eat) {
				EntityPlayerMP mp = (EntityPlayerMP) player;
				mp.getServerForPlayer().getEntityTracker().func_151248_b(mp, new S0BPacketAnimation(mp, 3));
			}
		}
		
		event.setCanceled(true);
		event.setResult(Event.Result.DENY);
	}
	
	/**
	 * This event occurs when player starting item use.
	 *
	 * @param event UseItemStart event.This event occurs every second at player using item.
	 */
	@SubscribeEvent
	public void onPlayerUseItem_Start (PlayerUseItemEvent.Start event) {
		if (!event.entityPlayer.worldObj.isRemote)
			return;                                                                //サーバーだったら無視
		if (!(event.entityPlayer instanceof EntityOtherPlayerMP))
			return;                                                //EntityOtherPlayerMPじゃなかったら無視
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.item.getTagCompound());    //タグの取得
		if (compound == null)
			return;                                                                                    //タグが無かったら(null)無視
		//event.entityPlayer.addChatComponentMessage(new ChatComponentTranslation((event.entityPlayer.worldObj.isRemote ? "[Client]" : "[Server]") + "Use_Start:" + event.duration));
		EntityOtherPlayerMP playerMP = (EntityOtherPlayerMP) event.entityPlayer;                                        //Entityの取得
		int eatingTime = compound.getInteger("EatingTime");                                                    //食べる時間を取得
		event.duration = (eatingTime <= 0 ? 32 : eatingTime) + 27;                                                        //EntityOtherPlayerMPでdurationが既定値になるから上書き(26はバニラでupdateItemUseが起きないように)
	}
	
	/**
	 * This event occurs every tick at player using item.
	 *
	 * @param event UseItemTick event.
	 */
	@SubscribeEvent
	public void onPlayerUseItem_Tick (PlayerUseItemEvent.Tick event) {
		if (event.item == null) return;
		NBTTagCompound compound = FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(event.item.getTagCompound());    //タグの取得
		if (compound == null)
			return;                                                                                    //タグが無かったら(null)無視
		int eatingTime = compound.getInteger("EatingTime");
		//event.entityPlayer.addChatComponentMessage(new ChatComponentTranslation((event.entityPlayer.worldObj.isRemote ? "[Client]" : "[Server]") + "Use_Tick:" + event.duration));
		if (event.duration <= 27) {                            //Durationが27以下だった場合Finishの処理
			ItemStack itemStack = event.item;
			EntityPlayer player = event.entityPlayer;
			int i = itemStack.stackSize;
			--itemStack.stackSize;
			player.worldObj.playSoundAtEntity(player, "random.burp", 0.5F, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			onEaten(itemStack);
			player.clearItemInUse();
			event.duration = 0;
			if (itemStack.stackSize != i) {
				player.inventory.mainInventory[player.inventory.currentItem] = itemStack;
				if (itemStack.stackSize <= 0) {
					player.inventory.mainInventory[player.inventory.currentItem] = null;
				}
			}
		} else if (event.duration <= 52 && event.duration % 4 == 0) {
			ItemStack itemStack = event.item;
			EntityPlayer player = event.entityPlayer;
			for (int j = 0; j < 5; ++j) {                    //パーティクルの計算
				Vec3 vec3 = Vec3.createVectorHelper(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				vec3.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0F);
				vec3.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);
				Vec3 vec31 = Vec3.createVectorHelper(((double) this.random.nextFloat() - 0.5D) * 0.3D, (double) (-this.random.nextFloat()) * 0.6D - 0.3D, 0.6D);
				vec31.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0F);
				vec31.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0F);
				vec31 = vec31.addVector(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
				String s = "iconcrack_" + Item.getIdFromItem(itemStack.getItem());
				
				if (itemStack.getHasSubtypes()) {
					s = s + "_" + itemStack.getItemDamage();
				}
				
				player.worldObj.spawnParticle(s, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord);        //パーティクル発生!!
			}
			
			player.playSound("random.eat", 0.5F + 0.5F * (float) this.random.nextInt(2),
					(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);                        //食べてる音を鳴らそうね
		}
		
	}
	
	private void onEaten (ItemStack stack) {
		
	}
}
