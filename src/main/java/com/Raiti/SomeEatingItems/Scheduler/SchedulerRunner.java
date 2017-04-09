package com.Raiti.SomeEatingItems.Scheduler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START;

/**
 * Forge's tick event handler.
 * <br>Created by Raiti-chan on 2017/03/29.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class SchedulerRunner {
	
	
	//==RegisterInstance================================================================================================
	
	/**
	 * World tick start schedule instance.
	 */
	public static final ScheduleTaskRegister WORLD_START = new ScheduleTaskRegister();
	
	/**
	 * World tick end schedule instance.
	 */
	public static final ScheduleTaskRegister WORLD_END = new ScheduleTaskRegister();
	
	/**
	 * Player tick start schedule instance.
	 */
	public static final PlayerScheduleTaskRegister PLAYER_START = new PlayerScheduleTaskRegister();
	
	/**
	 * Player tick end schedule instance.
	 */
	public static final PlayerScheduleTaskRegister PLAYER_END = new PlayerScheduleTaskRegister();
	
	/**
	 * Client Tick start schedule instance.
	 */
	public static final ScheduleTaskRegister CLIENT_START = new ScheduleTaskRegister();
	
	/**
	 * Client Tick end schedule instance.
	 */
	public static final ScheduleTaskRegister CLIENT_END = new ScheduleTaskRegister();
	
	/**
	 * Server Tick start schedule instance.
	 */
	public static final ScheduleTaskRegister SERVER_START = new ScheduleTaskRegister();
	
	/**
	 * Server Tick end schedule instance.
	 */
	public static final ScheduleTaskRegister SERVER_END = new ScheduleTaskRegister();
	
	/**
	 * Render Tick start schedule instance.
	 */
	public static final ScheduleTaskRegister RENDER_START = new ScheduleTaskRegister();
	
	/**
	 * Render Tick end schedule instance.
	 */
	public static final ScheduleTaskRegister RENDER_END = new ScheduleTaskRegister();
	
	/**
	 * Get the instance of Register.
	 *
	 * @param type  tick event type.
	 * @param phase event phase.
	 * @return Register instance.
	 */
	@NotNull
	public static ScheduleTaskRegister getRegisterInstance (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase) {
		switch (type) {
			case WORLD:
				return phase == START ? WORLD_START : WORLD_END;
			case PLAYER:
				return phase == START ? PLAYER_START : PLAYER_END;
			case CLIENT:
				return phase == START ? CLIENT_START : CLIENT_END;
			case SERVER:
				return phase == START ? SERVER_START : SERVER_END;
			case RENDER:
				return phase == START ? RENDER_START : RENDER_END;
		}
		return null;
	}
	
	//==================================================================================================================
	
	
	
	private static boolean isInitialized = false;
	
	public static void schedulerInitialize () {
		if (isInitialized) return;
		isInitialized = true;
		MinecraftForge.EVENT_BUS.register(new SchedulerRunner());
	}
	
	private SchedulerRunner(){}
	
	/**
	 * Player Tick Event.<br>
	 * Occours when TickStart and TickEnd.
	 *
	 * @param event Tick event.
	 */
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void playerTickEvent (TickEvent.PlayerTickEvent event) {
		//Server and client.
		if (event.player.getEntityWorld().isRemote) return;
		getRegisterInstance(event.type, event.phase).run();
		//Server only.
	}
	
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void playerLoggedOutEvent (PlayerEvent.PlayerLoggedOutEvent event) {
		PLAYER_START.clear(event.player);
		PLAYER_END.clear(event.player);
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
		getRegisterInstance(event.type, event.phase).run();
	}
	
	
}
