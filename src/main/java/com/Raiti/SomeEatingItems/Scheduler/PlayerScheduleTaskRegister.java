package com.Raiti.SomeEatingItems.Scheduler;


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
	PlayerScheduleTaskRegister () {
	}
	
	void clear(EntityPlayer player) {
		tasks.forEach(schedulerTask -> {
			if (((PlayerSchedulerTask)schedulerTask).getPlayer().equals(player))remove(schedulerTask);
		});
	}
	
}
