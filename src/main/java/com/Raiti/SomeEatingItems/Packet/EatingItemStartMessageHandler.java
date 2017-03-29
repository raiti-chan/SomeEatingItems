package com.Raiti.SomeEatingItems.Packet;

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
public class EatingItemStartMessageHandler implements IMessageHandler<EatingItemStartMessage, IMessage> {
	
	
	/**
	 * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
	 * is needed.
	 *
	 * @param message The message
	 * @param ctx     context
	 * @return an optional return message
	 */
	@Override
	public IMessage onMessage (EatingItemStartMessage message, MessageContext ctx) {
		return null;
	}
}
