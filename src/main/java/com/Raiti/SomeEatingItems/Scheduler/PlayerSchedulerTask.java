package com.Raiti.SomeEatingItems.Scheduler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * <br>Created by Raiti-chan on 2017/04/09.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PlayerSchedulerTask extends SchedulerTask {
	
	/**
	 * Player.
	 */
	private final EntityPlayer player;
	
	/**
	 * Allocates a new {@link PlayerSchedulerTask} object so that it has {@code runnable} as its run object.
	 * And register to {@link ScheduleTaskRegister}.
	 *
	 * @param type     Type of tick executed.
	 *                 This argument can not be null.
	 * @param phase    The phase of the tick executed.
	 *                 This argument can not be null.
	 * @param priority The priority of this task.
	 * @param runnable the object whose {@code run} method is invoked when this task is started.
	 *                 If {@code null}, this task's run method is invoked.
	 */
	public PlayerSchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase, @NotNull EntityPlayer player, byte priority, SchedulerRunnable runnable) {
		super(type, phase, priority, runnable);
		this.player = player;
	}
	
	/**
	 * Allocates a new {@link PlayerSchedulerTask} object.
	 * And register to {@link ScheduleTaskRegister}.
	 * This constructor has the same effect as {@linkplain PlayerSchedulerTask(SchedulerRunnable)}{@code (type, phase, priority, null)}.
	 *
	 * @param type     Type of tick executed.
	 *                 This argument can not be null.
	 * @param phase    The phase of the tick executed.
	 *                 This argument can not be null.
	 * @param priority The priority of this task.
	 */
	public PlayerSchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase, @NotNull EntityPlayer player, byte priority) {
		this(type, phase, player, priority, null);
	}
	
	/**
	 * Allocates a new {@link PlayerSchedulerTask} object.
	 * And register to {@link ScheduleTaskRegister}.
	 * This constructor has the same effect as {@linkplain PlayerSchedulerTask(SchedulerRunnable)}{@code (type, phase, 0, null)}.
	 *
	 * @param type  Type of tick executed.
	 *              This argument can not be null.
	 * @param phase The phase of the tick executed.
	 */
	public PlayerSchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase, @NotNull EntityPlayer player) {
		this(type, phase, player, (byte) 0, null);
	}
	
	
	public EntityPlayer getPlayer () {
		return player;
	}
	
	
	
	
}