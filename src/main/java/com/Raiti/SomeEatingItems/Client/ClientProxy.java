package com.Raiti.SomeEatingItems.Client;

import com.Raiti.SomeEatingItems.Server.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * <br>Created by Raiti-chan on 2017/03/06.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
	
	/**
	 * Get to Client's {@link EntityPlayer} instance.
	 * @return Return Client player.
	 */
	@Override
	public EntityPlayer getEntityPlayerInstance () {
		return Minecraft.getMinecraft().thePlayer;
	}
}
