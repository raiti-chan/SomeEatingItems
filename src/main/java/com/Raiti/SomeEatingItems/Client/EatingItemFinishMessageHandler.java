package com.Raiti.SomeEatingItems.Client;

import com.Raiti.SomeEatingItems.Packet.EatingItemFinishMessage;
import com.Raiti.SomeEatingItems.SomeEatingItems;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * <br>Created by Raiti-chan on 2017/03/28.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class EatingItemFinishMessageHandler implements IMessageHandler<EatingItemFinishMessage, IMessage> {
	
	/**
	 * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
	 * is needed.
	 *
	 * @param message The message
	 * @param ctx MessageContext
	 * @return an optional return message
	 */
	@Override
	public IMessage onMessage (EatingItemFinishMessage message, MessageContext ctx) {
		SomeEatingItems.proxy.getEntityPlayerInstance().resetActiveHand();
		return null;
	}
}