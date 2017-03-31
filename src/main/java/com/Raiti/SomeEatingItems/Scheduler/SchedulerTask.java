package com.Raiti.SomeEatingItems.Scheduler;

/**
 * A <i>SchedulerTask</i> is a schedule of execution in a Minecraft's tick.
 * <p>
 * Every Task has a priority.
 * Task with higher priority are executed in preference to task with lower priority.
 * <p>
 * Scheduler continues to execute Tasks until either of the following occurs:
 * <ul>
 * <li>The {@link SchedulerTask#stop()} method has been called.</li>
 * <li>By returning from the call to the <code>run</code> method if not loop task.</li>
 * <li>The specified time has elapsed.</li>
 * </ul>
 * <p>
 * There are two ways to create a new task of execution.
 * One is to declare a class to be a subclass of <code>SchedulerTask</code>.
 * This subclass should override the {@link SchedulerTask#run()} method of class {@link SchedulerTask}.
 * An instance of the subclass can then be allocated  and started.
 * For example, a task that output message:
 * <hr><blockquote><pre>
 *     class TestTask extend SchedulerTask {
 *         public void run() {
 *             System.out.println("HelloWorld");
 *         }
 *     }
 * </pre></blockquote><hr>
 * <p>
 * The following code would then create a Task and start it running after 5 second:
 * <blockquote><pre>
 *     TestTask task = new TestTask();
 *     task.setStartDelay(100);
 *     task.start();
 * </pre></blockquote>
 * <p>
 * The other way to create a task is to declare a class that implements
 * the {@link SchedulerRunnable} interface.
 * That class then implements the {@link SchedulerRunnable#run()} method.
 * An instance of the class can then be allocated, passed as an argument
 * when creating {@link SchedulerRunnable}, and started.
 * The same example in this other style looks like the following:
 * <hr><blockquote><pre>
 *      class TestTRun implements SchedulerRunnable {
 *          public void run() {
 *              System.out.println("HelloWorld");
 *          }
 *      }
 *  </pre></blockquote><hr>
 * <p>
 * The following code would then create a task and start it running:
 * <blockquote><pre>
 *     TestRun run = new TestRun();
 *     SchedulerTask task = new SchedulerTask();
 *     task.setStartDelay(100);
 *     task.start();
 * </pre></blockquote>
 * <p>
 * Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be thrown.
 * <br>Created by Raiti-chan on 2017/03/29.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @see SchedulerRunnable
 * @see #run()
 * @see #stop()
 * @see #setStartDelay(int)
 * @since 1.0.0
 */
public class SchedulerTask implements SchedulerRunnable {
	
	/**
	 * What will be run.
	 */
	private SchedulerRunnable runnable = null;
	
	/**
	 * Task priority.
	 */
	private byte priority = 0;
	
	private boolean isRunning = false;
	
	/**
	 * Allocates a new {@link SchedulerTask} object.
	 * This constructor has the same effect as {@linkplain SchedulerTask(SchedulerRunnable)}{@code (null)}.
	 */
	public SchedulerTask () {
		this(null);
	}
	
	/**
	 * Allocates a new {@link SchedulerTask} object so that it has {@code runnable} as its run object.
	 *
	 * @param runnable the object whose {@code run} method is invoked when this task is started.
	 *                 If {@code null}, this task's run method is invoked.
	 */
	public SchedulerTask (SchedulerRunnable runnable) {
		this.runnable = runnable;
	}
	
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used to create a Schedule,
	 * starting the Schedule causes the object's <code>run</code> method to be called in that separately executing Scheduler.
	 * <p>
	 * The general contract of method <code>run</code> is that it may take any action whatsoever.
	 */
	@Override
	public void run () {
		if (runnable != null) {
			runnable.run();
		}
	}
	
	
	public void start() {
		ScheduleTaskRegister.getInstance().add(this);
		isRunning = true;
	}
	
	/**
	 * Forcibly stop the task.
	 * In many cases, it is used for a perpetual loop schedule.
	 * But A task that is already executing the run method is not stopped until it recurs.
	 */
	public void stop () {
	
	}
	
	/**
	 * Specify the delay time before the task is started
	 * After the specified time (Tick) has elapsed, execution of the scheduled task is started.
	 * But loop schedule loop cycle interval time is not this method.
	 *
	 * @param tick delay time (20tick = 1second)
	 */
	public void setStartDelay (int tick) {
	
	}
	
	/**
	 * Specify the number of loop.
	 * If you specify a number or less 0, that is endless loop.
	 *
	 * @param value number of loop.
	 */
	public void setLoopOfNumber (int value) {
	
	}
	
	/**
	 * Specify to cycle interval time.
	 *
	 * @param tick This value must be a value greater than or equal to 0.
	 *             If you specify a number less than 0, an {@link IllegalArgumentException} will be thrown.
	 * @throws IllegalArgumentException Thrown if the argument is less than or equal to 0.
	 */
	public void setLoopPeriod (int tick) throws IllegalArgumentException {
	
	}
	
	
	
	public void setPriority (byte priority) {
		this.priority = priority;
	}
	
	
	public byte getPriority () {
		return this.priority;
	}
	
}
