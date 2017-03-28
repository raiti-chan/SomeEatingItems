package com.Raiti.SomeEatingItems.Packet;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.netty.buffer.ByteBuf;

/**
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class RightClickItemMessage implements IMessage {
	
	/**
	 * Convert from the supplied buffer into your specific message type
	 *
	 * @param buf buf
	 */
	@Override
	public void fromBytes (ByteBuf buf) {
		
	}
	
	/**
	 * Deconstruct your message into the supplied byte buffer
	 *
	 * @param buf buf
	 */
	@Override
	public void toBytes (ByteBuf buf) {
		
	}
}
