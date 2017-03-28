package com.Raiti.SomeEatingItems.Packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * <br>Created by Raiti-chan on 2017/03/05.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class RightClickItemMessageHandler implements IMessageHandler<RightClickItemMessage, IMessage> {
	
	
	/**
	 * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
	 * is needed.
	 *
	 * @param message The message
	 * @param ctx     context
	 * @return an optional return message
	 */
	@Override
	public IMessage onMessage (RightClickItemMessage message, MessageContext ctx) {
		/*
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		WorldServer worldserver = MinecraftServer.worldServerForDimension(player.dimension);
		PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(player, PlayerInteractEvent.Action.RIGHT_CLICK_AIR, 0, 0, 0, -1, worldserver);
		if (event.getResult() != Event.Result.DENY) {
			player.theItemInWorldManager.tryUseItem(player, worldserver, player.getHeldItem());
		}
		*/
		return null;
	}
}
