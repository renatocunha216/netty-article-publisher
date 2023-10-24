package br.com.rbcti.server.handlers;

import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.commands.Command;
import br.com.rbcti.publisher.common.messages.SimpleMessage;
import br.com.rbcti.server.commands.CommandFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class ServerHandler extends SimpleChannelInboundHandler<SimpleMessage> {

    private static final Logger LOGGER = LogManager.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleMessage msg) throws Exception {

        LOGGER.debug("Message received: {}", msg);

        Command command = CommandFactory.getCommand(msg.getMessageId());

        if (command != null) {
            command.execute(ctx, msg);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        SocketAddress address = ctx.channel().remoteAddress();
        LOGGER.info("Connected with {}.", address);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketAddress address = ctx.channel().remoteAddress();
        LOGGER.info("Disconnected from {}.", address);
    }

}