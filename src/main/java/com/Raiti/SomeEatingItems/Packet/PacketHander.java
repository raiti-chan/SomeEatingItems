package com.Raiti.SomeEatingItems.Packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.Raiti.SomeEatingItems.SomeEatingItems;


/**
 * <br>Created by Raiti-chan on 2017/03/04.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class PacketHander {
	
	/**
	 * PacketChannel Instance
	 */
	@SuppressWarnings("WeakerAccess")
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SomeEatingItems.MOD_ID);
	
	
	public static void init() {
		INSTANCE.registerMessage(RightClickItemMessageHandler.class, RightClickItemMessage.class, 0, Side.SERVER);
	}
	
}
