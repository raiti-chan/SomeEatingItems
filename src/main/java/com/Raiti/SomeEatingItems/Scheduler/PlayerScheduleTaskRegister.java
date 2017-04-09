package com.Raiti.SomeEatingItems.Scheduler;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

/**
 * <br>Created by Raiti-chan on 2017/04/09.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class PlayerScheduleTaskRegister extends ScheduleTaskRegister {
	
	/**
	 * Prohibit the creation of instances of this class.
	 */
	protected PlayerScheduleTaskRegister () {
	}
	
	@Override
	protected void add (SchedulerTask task) {
		if (!(task instanceof PlayerSchedulerTask))throw new IllegalArgumentException("PlayerScheduleTaskRegister is can't add SchedulerTask");
		super.add(task);
	}
	
	protected void clear(EntityPlayer player) {
		tasks.forEach(task -> {
			if (((PlayerSchedulerTask)task).getPlayer().equals(player))remove(task);
		});
	}
	
	public List<PlayerSchedulerTask> getPlayerTasks(EntityPlayer player) {
		List<PlayerSchedulerTask> list = new ArrayList<>();
		tasks.forEach(task -> {
			if (((PlayerSchedulerTask)task).getPlayer().equals(player))list.add((PlayerSchedulerTask) task);
		});
		return list;
	}
	
}
