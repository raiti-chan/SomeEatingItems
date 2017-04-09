package com.Raiti.SomeEatingItems.Scheduler;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START;

/**
 * The register class of the scheduler task.
 * <p>
 * <br>Created by Raiti-chan on 2017/03/30.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("WeakerAccess")
public class ScheduleTaskRegister {

	/**
	 * Task list.
	 */
	protected List<SchedulerTask> tasks = new ArrayList<>();
	
	/**
	 * Add buffer.
	 */
	protected List<SchedulerTask> buffer = new ArrayList<>();
	
	/**
	 * Remove task buffer.
	 */
	protected List<SchedulerTask> removeBuffer = new ArrayList<>();
	
	/**
	 * True if there is a change to Register.
	 * It is false when the {@link #applyChangesToRegister()} is executed.
	 */
	protected boolean isChanged = false;
	
	/**
	 * If {@link #clear()} method executed, will be true.
	 */
	protected boolean clearFlag = false;
	
	/**
	 * Functions to add to the list in descending order of priority.
	 *
	 * @param list List to be added.
	 * @param task Tasks to add.
	 */
	protected static void adder (List<SchedulerTask> list, SchedulerTask task) {
		if (list.isEmpty()) {
			list.add(task);
			return;
		}
		int i = 0;
		boolean flag = false;
		for (SchedulerTask t : list) {
			if (t.getPriority() < task.getPriority()) {
				list.add(i, task);
				flag = true;
				break;
			}
			i++;
		}
		if (!flag) list.add(task);
	}
	
	/**
	 * Prohibit the creation of instances of this class.
	 */
	protected ScheduleTaskRegister () {
	}
	
	/**
	 * Apply changes to Register.
	 */
	protected void applyChangesToRegister () {
		tasks.removeAll(removeBuffer);
		if (this.tasks.isEmpty()) { //タスクが空の場合
			this.tasks = this.buffer; //空なのでそのままバッファを入れる
			this.buffer = new ArrayList<>(); //バッファは新しいリストに
		} else { //タスクが既に存在していた場合
			List<SchedulerTask> buf = new ArrayList<>(); //一時的なバッファを作成
			int bufferIndex = 0, taskIndex = 0; //バッファとタスクのインデックス
			final int bufferSize = this.buffer.size(), taskSize = this.tasks.size(); //バッファとタスクのサイズ
			
			while (bufferIndex < bufferSize && taskIndex < taskSize) {
				SchedulerTask bufTask = this.buffer.get(bufferIndex);
				SchedulerTask task = this.tasks.get(taskIndex);
				
				if (task.getPriority() <= bufTask.getPriority()) {
					buf.add(bufTask);
					bufferIndex++;
				} else {
					buf.add(task); //バッファへ追加
					taskIndex++;
				}
				
			}
			while (bufferIndex < bufferSize) {
				buf.add(this.buffer.get(bufferIndex));
				bufferIndex++;
			}
			while (taskIndex < taskSize) {
				buf.add(this.tasks.get(taskIndex));
				taskIndex++;
			}
			tasks = buf;
			buffer.clear();
			removeBuffer.clear();
		}
		isChanged = false;
	}
	
	/**
	 * Add a task to the schedule.
	 * But it only adds to the buffer, it is actually added after the Tick process is over.
	 * For example, even a task with a delay of 0 will be executed on the next tick.
	 *
	 * @param task Task to add.
	 */
	protected void add (SchedulerTask task) {
		adder(buffer, task);
		isChanged = true;
	}
	
	/**
	 * Remove a task from the schedule.
	 *
	 * @param task Object of the task to erase
	 */
	protected void remove (SchedulerTask task) {
		adder(removeBuffer, task);
		isChanged = true;
	}
	
	/**
	 * Clear to register.
	 * This method also remove changes to the register.
	 */
	@SuppressWarnings("unused")
	protected void clear () {
		clearFlag = true;
	}
	
	/**
	 * Execute the task.
	 * It is not recommended to run this method arbitrarily.
	 * Normally, this method is executed at a predetermined timing.
	 */
	protected void run () {
		this.tasks.forEach(SchedulerTask::runTry);//登録されているスケジュールを実行
		if (clearFlag) {
			tasks.clear();
			buffer.clear();
			removeBuffer.clear();
			clearFlag = false;
			isChanged = false;
			return;
		}
		if (!isChanged) return;//変更が無ければ帰す
		applyChangesToRegister();//バッファへ溜められたレジスターへの変更を反映する。
	}
	
	
}
