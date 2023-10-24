package br.com.rbcti.publisher.common.commands;

import br.com.rbcti.publisher.common.messages.SimpleMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author Renato Cunha
 *
 */
public interface Command {

    public void execute(ChannelHandlerContext  ctx, SimpleMessage message);

}
