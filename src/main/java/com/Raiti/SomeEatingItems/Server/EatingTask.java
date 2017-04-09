package com.Raiti.SomeEatingItems.Server;

import java.util.Random;

import com.Raiti.SomeEatingItems.FoodMetaDataStructure;
import com.Raiti.SomeEatingItems.Packet.EatingItemFinishMessage;
import com.Raiti.SomeEatingItems.Packet.PacketHander;
import com.Raiti.SomeEatingItems.Scheduler.PlayerSchedulerTask;
import com.Raiti.SomeEatingItems.Scheduler.ScheduleTaskRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/04/09.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class EatingTask extends PlayerSchedulerTask {
	
	private static final Random RANDOM = new Random();
	
	private int count = 0;
	
	/**
	 * Allocates a new {@link EatingTask} object.
	 * And register to {@link ScheduleTaskRegister}.
	 *
	 * @param player The player of the bind this event.
	 */
	@SuppressWarnings("WeakerAccess")
	public EatingTask (@NotNull EntityPlayerMP player) {
		super(TickEvent.Phase.END, player);
		int time = FoodMetaDataStructure.getEatingTime(FoodMetaDataStructure.getFoodMetaDataStructureNBTTagCompound(
				player.inventory.getCurrentItem().getTagCompound()));
		this.setIntervalTime(0);
		this.setStartDelay(0);
		this.setLoopCount(time);
		
	}
	
	@Override
	public void run () {
		if (count % 4 == 0) updateItemUse((EntityPlayerMP) getPlayer(), getPlayer().inventory.getCurrentItem(), 5);
		count++;
	}
	
	@Override
	public void finish () {
		EntityPlayerMP player = (EntityPlayerMP) getPlayer();
		ItemStack stack = player.inventory.getCurrentItem();
		updateItemUse(player, stack, 16);
		stack.shrink(1);
		player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, player.world.rand.nextFloat() * 0.1F + 0.9F);
		onEaten(stack);
		PacketHander.INSTANCE.sendTo(new EatingItemFinishMessage(), player);
		player.setHeldItem(player.getActiveHand(), stack.getCount() <= 0 ? ItemStack.EMPTY : stack);
		player.connection.sendPacket(new SPacketHeldItemChange(player.inventory.currentItem));
	}
	
	@Override
	public void stopped () {
		dispose();
	}
	
	/**
	 * Update to Sound and Particle.
	 *
	 * @param entity              player.
	 * @param stack               item.
	 * @param eatingParticleCount particle count.
	 */
	private void updateItemUse (EntityPlayerMP entity, ItemStack stack, int eatingParticleCount) {
		for (int i = 0; i < eatingParticleCount; i++) { //パーティクルの計算
			Vec3d vec3d = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			vec3d = vec3d.rotatePitch(-entity.rotationPitch * 0.017453292F);
			vec3d = vec3d.rotateYaw(-entity.rotationYaw * 0.017453292F);
			double d0 = (double) (-RANDOM.nextFloat()) * 0.6D - 0.3D;
			Vec3d vec3d1 = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
			vec3d1 = vec3d1.rotatePitch(-entity.rotationPitch * 0.017453292F);
			vec3d1 = vec3d1.rotateYaw(-entity.rotationYaw * 0.017453292F);
			vec3d1 = vec3d1.addVector(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
			
			
			if (stack.getHasSubtypes()) //アイテムがメタデータを持っている場合
				((WorldServer) entity.world).spawnParticle(EnumParticleTypes.ITEM_CRACK, EnumParticleTypes.ITEM_CRACK.getShouldIgnoreRange(), vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, 0, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord, 1,
						Item.getIdFromItem(stack.getItem()), stack.getMetadata()); //パーティクルの発生
			else //無い場合
				((WorldServer) entity.world).spawnParticle(EnumParticleTypes.ITEM_CRACK, EnumParticleTypes.ITEM_CRACK.getShouldIgnoreRange(), vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, 0, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord, 1,
						Item.getIdFromItem(stack.getItem())); //パーティクルの発生
		}
		
		entity.getEntityWorld().playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.5F + 0.5F * (float) RANDOM.nextInt(2), (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
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
