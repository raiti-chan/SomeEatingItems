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
	
	//==RegisterInstance================================================================================================
	
	/**
	 * World tick start schedule instance.
	 */
	private static final ScheduleTaskRegister WORLD_START = new ScheduleTaskRegister();
	
	/**
	 * World tick end schedule instance.
	 */
	private static final ScheduleTaskRegister WORLD_END = new ScheduleTaskRegister();
	
	/**
	 * Player tick start schedule instance.
	 */
	private static final ScheduleTaskRegister PLAYER_START = new ScheduleTaskRegister();
	
	/**
	 * Player tick end schedule instance.
	 */
	private static final ScheduleTaskRegister PLAYER_END = new ScheduleTaskRegister();
	
	/**
	 * Client Tick start schedule instance.
	 */
	private static final ScheduleTaskRegister CLIENT_START = new ScheduleTaskRegister();
	
	/**
	 * Client Tick end schedule instance.
	 */
	private static final ScheduleTaskRegister CLIENT_END = new ScheduleTaskRegister();
	
	/**
	 * Server Tick start schedule instance.
	 */
	private static final ScheduleTaskRegister SERVER_START = new ScheduleTaskRegister();
	
	/**
	 * Server Tick end schedule instance.
	 */
	private static final ScheduleTaskRegister SERVER_END = new ScheduleTaskRegister();
	
	/**
	 * Render Tick start schedule instance.
	 */
	private static final ScheduleTaskRegister RENDER_START = new ScheduleTaskRegister();
	
	/**
	 * Render Tick end schedule instance.
	 */
	private static final ScheduleTaskRegister RENDER_END = new ScheduleTaskRegister();
	
	/**
	 * Get the instance of Register.
	 *
	 * @param type  tick event type.
	 * @param phase event phase.
	 * @return Register instance.
	 */
	@NotNull
	static ScheduleTaskRegister getRegisterInstance (@NotNull TickEvent.Type type, @NotNull TickEvent.Phase phase) {
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
	
	/**
	 * Task list.
	 */
	private List<SchedulerTask> tasks = new ArrayList<>();
	
	/**
	 * Add buffer.
	 */
	private List<SchedulerTask> buffer = new ArrayList<>();
	
	/**
	 * Remove task buffer.
	 */
	private List<SchedulerTask> removeBuffer = new ArrayList<>();
	
	/**
	 * True if there is a change to Register.
	 * It is false when the {@link #applyChangesToRegister()} is executed.
	 */
	private boolean isChanged = false;
	
	/**
	 * Functions to add to the list in descending order of priority.
	 *
	 * @param list List to be added.
	 * @param task Tasks to add.
	 */
	private static void adder (List<SchedulerTask> list, SchedulerTask task) {
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
	private ScheduleTaskRegister () {
	}
	
	/**
	 * Apply changes to Register.
	 */
	private void applyChangesToRegister () {
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
	void add (SchedulerTask task) {
		adder(buffer, task);
		isChanged = true;
	}
	
	/**
	 * Remove a task from the schedule.
	 *
	 * @param task Object of the task to erase
	 */
	void remove (SchedulerTask task) {
		adder(removeBuffer, task);
		isChanged = true;
	}
	
	/**
	 * Execute the task.
	 * It is not recommended to run this method arbitrarily.
	 * Normally, this method is executed at a predetermined timing.
	 */
	void run () {
		this.tasks.forEach(SchedulerTask::runTry);//登録されているスケジュールを実行
		if (!isChanged) return;//変更が無ければ帰す
		applyChangesToRegister();//バッファへ溜められたレジスターへの変更を反映する。
	}
	
	
}
