package com.Raiti.SomeEatingItems.Scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * The register class of the scheduler task.
 * This class is a singleton class with only one instance.
 * <br>Created by Raiti-chan on 2017/03/30.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class ScheduleTaskRegister {
	
	/**
	 * Instance
	 */
	private static final ScheduleTaskRegister INSTANCE = new ScheduleTaskRegister();
	
	/**
	 * Get the only instance of this class.
	 *
	 * @return class instance.
	 */
	public static ScheduleTaskRegister getInstance () {
		return INSTANCE;
	}
	
	/**
	 * Prohibit the creation of instances of this class.
	 */
	private ScheduleTaskRegister () {
	}
	
	
	/**
	 * Task list.
	 */
	private List<SchedulerTask> tasks = new ArrayList<>();
	
	
	void add (SchedulerTask task) {
		int i = 0;
		for (SchedulerTask t : tasks) {
			if (t.getPriority() > task.getPriority()) {
				tasks.add(i, task);
				break;
			}
			i++;
		}
	}
	
	
}
