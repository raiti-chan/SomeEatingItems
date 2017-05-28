package com.Raiti.SomeEatingItems.Server;

import com.Raiti.SomeEatingItems.Packet.EatingItemStopMessage;
import com.Raiti.SomeEatingItems.Scheduler.SchedulerRunner;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * <br>Created by Raiti-chan on 2017/03/28.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class EatingItemStopMessageHandler implements IMessageHandler<EatingItemStopMessage, IMessage> {
	
	/**
	 * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
	 * is needed.
	 *
	 * @param message The message
	 * @param ctx     MessageContext
	 * @return an optional return message
	 */
	@Override
	public IMessage onMessage (EatingItemStopMessage message, MessageContext ctx) {
		//ctx.getServerHandler().playerEntity.sendMessage(new TextComponentString("[Server]H[" + message.getActiveHand() + "]EatingStop-" + ctx.getServerHandler().playerEntity.getHeldItem(message.getActiveHand())));
		SchedulerRunner.PLAYER_END.getPlayerTasks(ctx.getServerHandler().playerEntity).forEach(playerSchedulerTask -> {
			if (playerSchedulerTask instanceof EatingTask) playerSchedulerTask.stop();
		});
		return null;
	}
	
}
