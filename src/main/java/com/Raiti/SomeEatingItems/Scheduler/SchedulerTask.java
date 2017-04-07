package com.Raiti.SomeEatingItems.Scheduler;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

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
@SuppressWarnings({"WeakerAccess", "unused"})
public class SchedulerTask implements SchedulerRunnable {
	
	/**
	 * What will be run.
	 */
	private final SchedulerRunnable runnable;
	
	/**
	 * Type of tick executed.
	 */
	@NotNull
	private final TickEvent.Type tickEventType;
	
	/**
	 * The phase of the tick executed.
	 */
	@NotNull
	private final TickEvent.Phase tickEventPhase;
	
	/**
	 * Task priority.
	 */
	private byte priority = 0;
	
	/**
	 * True if this task is executable.
	 */
	private boolean isRunning = false;
	
	/**
	 * This is number of loop.
	 * If it repeats permanently it will be -1.
	 */
	private int loopCount = 1;
	
	/**
	 * The delay time until this task is executed.
	 */
	private int startDelayTime = 0;
	
	/**
	 * Cycle interval time.
	 */
	private int intervalTime = 0;
	
	/**
	 * The remaining number of repetitions.
	 */
	private int remainingLoopCount = 0;
	
	/**
	 * This task's delay time.
	 * This is for start delay time and interval time countdown.
	 */
	private int delayTimeCount = 0;
	
	/**
	 * Allocates a new {@link SchedulerTask} object so that it has {@code runnable} as its run object.
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
	public SchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase, byte priority, SchedulerRunnable runnable) {
		this.tickEventType = type;
		this.tickEventPhase = phase;
		this.runnable = runnable;
		this.priority = priority;
		ScheduleTaskRegister.getRegisterInstance(type, phase).add(this);
	}
	
	/**
	 * Allocates a new {@link SchedulerTask} object.
	 * And register to {@link ScheduleTaskRegister}.
	 * This constructor has the same effect as {@linkplain SchedulerTask(SchedulerRunnable)}{@code (type, phase, priority, null)}.
	 *
	 * @param type     Type of tick executed.
	 *                 This argument can not be null.
	 * @param phase    The phase of the tick executed.
	 *                 This argument can not be null.
	 * @param priority The priority of this task.
	 */
	public SchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase, byte priority) {
		this(type, phase, priority, null);
	}
	
	/**
	 * Allocates a new {@link SchedulerTask} object.
	 * And register to {@link ScheduleTaskRegister}.
	 * This constructor has the same effect as {@linkplain SchedulerTask(SchedulerRunnable)}{@code (type, phase, 0, null)}.
	 *
	 * @param type  Type of tick executed.
	 *              This argument can not be null.
	 * @param phase The phase of the tick executed.
	 *              This argument can not be null.
	 */
	public SchedulerTask (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase) {
		this(type, phase, (byte) 0, null);
	}
	
	
	/**
	 * When an object implementing interface <code>Runnable</code> is used to create a Schedule,
	 * starting the Schedule causes the object's <code>run</code> method to be called in that separately executing Scheduler.
	 * <p>
	 * The general contract of method <code>run</code> is that it may take any action whatsoever.
	 */
	@Override
	public void run () {
		if (this.runnable != null) {
			this.runnable.run();
		}
	}
	
	@Override
	public void finish () {
		this.dispose();
	}
	
	/**
	 * It will try to perform this task.
	 * When it is not executed, it is as follows:
	 * <ul>
	 * <li>If the specified start delay time has not elapsed.
	 * In this case, decrease the remaining delay time by 1.</li>
	 * <li></li>
	 * <li>The {@link #isRunning} field is false.</li>
	 * </ul>
	 */
	public void runTry () {
		if (!isRunning) return;
		if (delayTimeCount > 0) {
			delayTimeCount--;
			return;
		}
		run();
		delayTimeCount = intervalTime;
		if (loopCount == -1) return;
		remainingLoopCount--;
		if (remainingLoopCount <= 0) this.finish();
	}
	
	/**
	 * Make this task executable.
	 * However, it will not be executed immediately even if the task becomes executable.
	 * If this method is executed after the timing at which this task should be executed, it will be carried over to the next tick.
	 * For example, if this method of a task with priority 2 is executed among tasks with priority 1, the first execution will be the next tick.
	 */
	public void start () {
		delayTimeCount = startDelayTime;
		remainingLoopCount = loopCount;
		isRunning = true;
	}
	
	/**
	 * Forcibly stop the task.
	 * In many cases, it is used for a perpetual loop schedule.
	 * But A task that is already executing the run method is not stopped until it recurs.
	 */
	public void stop () {
		isRunning = false;
		this.stopped();
	}
	
	/**
	 * Dispose a this task.
	 * Remove a task from {@link ScheduleTaskRegister} when executed this method.
	 */
	public void dispose () {
		ScheduleTaskRegister.getRegisterInstance(this.tickEventType, this.tickEventPhase).remove(this);
	}
	
	/**
	 * Increase current delay time.
	 * @param tick time. (20tick = 1second)
	 */
	public void increaseDelayTime (int tick) {
		delayTimeCount += tick;
	}
	
	/**
	 * Decrease current delay time.
	 * @param tick time. (20tick = 1second)
	 */
	public void decreaseDelayTime (int tick) {
		delayTimeCount -= tick;
	}
	
	/**
	 * Set the delay time to 0.
	 */
	public void delayTimeTo0 () {
		delayTimeCount = 0;
	}
	
	/**
	 * Increase current remaining loop count.
	 * @param count count.
	 */
	public void increaseRemainingLoopCount (int count) {
		remainingLoopCount += count;
	}
	
	/**
	 * Decrease current remaining loop count.
	 * @param count count.
	 */
	public void decreaseRemainingLoopCount (int count) {
		remainingLoopCount -= count;
	}
	
	/**
	 * Set the remaining loop count to 0.
	 */
	public void remainingLoopCountTo0 () {
		remainingLoopCount = 0;
	}
	
	
	/**
	 * Specify the delay time before the task is started
	 * After the specified time (Tick) has elapsed, execution of the scheduled task is started.
	 * But loop schedule loop cycle interval time is not this method.
	 * It can not be changed if it is in the executable state.
	 * If it is already waiting for execution, this change will be applied after restart.
	 *
	 * @param tick delay time (20tick = 1second)
	 */
	public void setStartDelay (int tick) {
		startDelayTime = tick;
	}
	
	/**
	 * Specify to cycle interval time.
	 * If it is waiting for execution, it will be applied in the next cycle.
	 * @param tick This value must be a value more than or equal to 0.
	 *             If you specify a number less than 0, an {@link IllegalArgumentException} will be thrown.
	 * @throws IllegalArgumentException Thrown if the argument is less than or equal to 0.
	 */
	public void setIntervalTime (int tick) throws IllegalArgumentException {
		if (tick < 0) throw new IllegalArgumentException("Time must be a value more than or equal to 0.");
		intervalTime = tick;
	}
	
	/**
	 * Specify the count of loop.
	 * If you specify a number or less 0, that is endless loop.
	 * But If you change while executing or waiting for execution, it will be changed after restart.
	 *
	 * @param value number of loop.
	 */
	public void setLoopCount (int value) {
		loopCount = value <= 0 ? -1 : value;
	}
	
	/**
	 * Get to this task's priority.
	 *
	 * @return this task's priority.
	 */
	public byte getPriority () {
		return this.priority;
	}
	
	/**
	 * Get {@link #isRunning}.
	 * @return boolean value of {@link #isRunning}
	 */
	public boolean isRunning () {
		return isRunning;
	}
	
	/**
	 * Get {@link #loopCount}.
	 * @return value of {@link #loopCount}
	 */
	public int getLoopCount () {
		return loopCount;
	}
	
	/**
	 * Get {@link #startDelayTime}.
	 * @return value of {@link #startDelayTime}
	 */
	public int getStartDelayTime () {
		return startDelayTime;
	}
	
	/**
	 * Get {@link #intervalTime}.
	 * @return value of {@link #intervalTime}
	 */
	public int getIntervalTime () {
		return intervalTime;
	}
	
	/**
	 * Get {@link #remainingLoopCount}.
	 * @return value of {@link #remainingLoopCount}
	 */
	public int getRemainingLoopCount () {
		return remainingLoopCount;
	}
	
	/**
	 * Get {@link #delayTimeCount}.
	 * @return value of {@link #delayTimeCount}.
	 */
	public int getDelayTimeCount () {
		return delayTimeCount;
	}
}
