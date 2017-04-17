package com.Raiti.SomeEatingItems.Packet;

import net.minecraft.util.EnumHand;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class EatingItemStartMessage implements IMessage {
	
	private byte handType;
	
	/**
	 * @param hand Active hand.
	 */
	public EatingItemStartMessage (@NotNull EnumHand hand) {
		handType = (byte) hand.ordinal();
	}
	
	/**
	 * This constructor is Not used.
	 * This is a constructor for instance creation by Forge's reflection.
	 */
	@SuppressWarnings("DeprecatedIsStillUsed")
	@Deprecated
	public EatingItemStartMessage(){
	}
	
	public EnumHand getActiveHand () {
		return EnumHand.values()[handType];
	}
	
	
	/**
	 * Convert from the supplied buffer into your specific message type
	 *
	 * @param buf buf
	 */
	@Override
	public void fromBytes (ByteBuf buf) {
		this.handType = buf.getByte(0);
	}
	
	/**
	 * Deconstruct your message into the supplied byte buffer
	 *
	 * @param buf buf
	 */
	@Override
	public void toBytes (ByteBuf buf) {
		buf.writeByte(this.handType);
	}
}
