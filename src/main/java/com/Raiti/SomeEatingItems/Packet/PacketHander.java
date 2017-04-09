package com.Raiti.SomeEatingItems.Packet;

import com.Raiti.SomeEatingItems.Client.EatingItemFinishMessageHandler;
import com.Raiti.SomeEatingItems.Server.EatingItemStartMessageHandler;
import com.Raiti.SomeEatingItems.Server.EatingItemStopMessageHandler;
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
		INSTANCE.registerMessage(EatingItemStartMessageHandler.class, EatingItemStartMessage.class, 0, Side.SERVER);
		INSTANCE.registerMessage(EatingItemFinishMessageHandler.class, EatingItemFinishMessage.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(EatingItemStopMessageHandler.class, EatingItemStopMessage.class, 2, Side.SERVER);
	}
	
	
}
