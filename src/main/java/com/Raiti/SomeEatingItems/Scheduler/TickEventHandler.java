package com.Raiti.SomeEatingItems.Scheduler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Forge's tick event handler.
 * <br>Created by Raiti-chan on 2017/03/29.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class TickEventHandler {
	
	
	/**
	 * Server Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void TickEvent (TickEvent.ServerTickEvent event) {
		switch (event.phase) {
			case START:
				break;
			case END:
				break;
		}
	}
	
}
