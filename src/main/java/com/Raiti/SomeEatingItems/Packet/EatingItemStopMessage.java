package com.Raiti.SomeEatingItems.Packet;

import net.minecraft.util.EnumHand;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/03/28.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class EatingItemStopMessage extends EatingItemStartMessage {
	/**
	 * @param hand Active hand.
	 */
	public EatingItemStopMessage (@NotNull EnumHand hand) {
		super(hand);
	}
	
	/**
	 * This constructor is Not used.
	 * This is a constructor for instance creation by Forge's reflection.
	 */
	@SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
	@Deprecated
	public EatingItemStopMessage () {
	}
}
