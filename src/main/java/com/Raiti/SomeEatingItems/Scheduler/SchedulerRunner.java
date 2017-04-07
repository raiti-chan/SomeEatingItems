package com.Raiti.SomeEatingItems.Scheduler;

import net.minecraftforge.common.MinecraftForge;
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
public class SchedulerRunner {
	
	private static boolean isInitialized = false;
	
	public static void schedulerInitialize () {
		if (isInitialized) return;
		isInitialized = true;
		MinecraftForge.EVENT_BUS.register(new SchedulerRunner());
	}
	
	private SchedulerRunner(){}
	
	
	/**
	 * World Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void worldTickEvent (TickEvent.WorldTickEvent event) {
		ScheduleTaskRegister.getRegisterInstance(event.type, event.phase).run();
	}
	
	
	/**
	 * Player Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void playerTickEvent (TickEvent.PlayerTickEvent event) {
		ScheduleTaskRegister.getRegisterInstance(event.type, event.phase).run();
	}
	
	
	/**
	 * Client Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void clientTickEvent (TickEvent.ClientTickEvent event) {
		ScheduleTaskRegister.getRegisterInstance(event.type, event.phase).run();
	}
	
	/**
	 * Server Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void serverTickEvent (TickEvent.ServerTickEvent event) {
		ScheduleTaskRegister.getRegisterInstance(event.type, event.phase).run();
	}
	
	
	/**
	 * Render Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void renderTickEvent (TickEvent.RenderTickEvent event) {
		ScheduleTaskRegister.getRegisterInstance(event.type, event.phase).run();
	}
	
	
}
