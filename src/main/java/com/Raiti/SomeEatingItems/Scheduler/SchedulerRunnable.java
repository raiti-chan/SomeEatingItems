package com.Raiti.SomeEatingItems.Scheduler;

/**
 * The <code>SchedulerRunnable</code> interface should be implemented by any class whose instance are intended to be executed by a schedule.
 * The class must define a method of no arguments called {@link SchedulerRunnable#run() run()}.
 * <p>
 * This interface is designed to provide a common protocol for objects that wish to execute code while they are active.
 * <code>SchedulerRunnable</code> is implemented by class {@link SchedulerTask}.
 * Being active simply means that a schedule has been started and has not yet been stopped.
 * <p>
 * In addition, <code>SchedulerRunnable</code> provides the means for a class to be active while not subclassing {@link SchedulerTask}.
 * A class that implements <code>SchedulerRunnable</code> can run without subclassing {@link SchedulerTask}
 * by instantiating a {@link SchedulerTask} instance and passing itself in as the target.
 * In most cases, the <code>SchedulerRunnable</code> interface should be used if you are only planning to override
 * the {@link SchedulerRunnable#run()} method and other {@link SchedulerRunnable} methods.
 * This is important because classes should not be subclassed unless the programmer intends on modifying or enhancing
 * the fundamental behavior of the class.
 *
 * <br>Created by Raiti-chan on 2017/03/29.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface SchedulerRunnable {
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used to create a Schedule,
	 * starting the Schedule causes the object's <code>run</code> method to be called in that separately executing Scheduler.
	 * <p>
	 * The general contract of method <code>run</code> is that it may take any action whatsoever.
	 */
	void run ();
	
}
