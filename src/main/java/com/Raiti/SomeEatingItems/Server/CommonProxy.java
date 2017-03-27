package com.Raiti.SomeEatingItems.Server;

import net.minecraft.entity.player.EntityPlayer;

/**
 * This mod's proxy class.
 * <br>Created by Raiti-chan on 2017/03/06.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommonProxy {
	
	/**
	 * Get to Client's {@link EntityPlayer} instance.
	 * @return If server, side only return null.
	 */
	@SuppressWarnings("unused")
	public EntityPlayer getEntityPlayerInstance () {
		return null;
	}
	
}
