package com.Raiti.SomeEatingItems.Packet;

import com.Raiti.SomeEatingItems.SomeEatingItems;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

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
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SomeEatingItems.MOD_ID);
	
	
	public static void init() {
		INSTANCE.registerMessage(RightClickItemMessageHandler.class, RightClickItemMessage.class, 0, Side.SERVER);
	}
	
}
