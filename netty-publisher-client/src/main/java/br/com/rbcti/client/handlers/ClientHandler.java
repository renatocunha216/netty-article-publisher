package br.com.rbcti.client.handlers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.LoginResponse;
import br.com.rbcti.publisher.common.messages.PostArticleRequest;
import br.com.rbcti.publisher.common.messages.PostArticleResponse;
import br.com.rbcti.publisher.common.messages.SimpleMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class ClientHandler extends SimpleChannelInboundHandler<SimpleMessage> {

    private static final long TIMEOUT = 1000L * 10;

    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);

    private volatile Channel channel;
    private final BlockingQueue<SimpleMessage> messagesReceived = new LinkedBlockingQueue<SimpleMessage>();


    private SimpleMessage getMessage() throws Exception {
        SimpleMessage msg = null;
        try {
            msg = messagesReceived.poll(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }

        if (msg == null) {
            throw new Exception("Timeout exception");
        }
        return msg;
    }

    private SimpleMessage getMessageAndCheck(long usn) throws Exception {
        SimpleMessage msg = getMessage();
        if (usn != msg.getUsn()) {
            throw new Exception("Invalid response");
        }
        return msg;
    }

    public synchronized LoginResponse login(LoginRequest request) throws Exception {

        LoginResponse response = null;

        Channel _channel = getChannel();

        if ((_channel != null) && (_channel.isActive())) {
            _channel.writeAndFlush(request);
            SimpleMessage simpleMessage = getMessageAndCheck(request.getUsn());

            response = (LoginResponse) simpleMessage;

        } else {
            throw new Exception("Client is disconnected.");
        }

        return response;
    }
    
    public synchronized PostArticleResponse postArticle(PostArticleRequest request) throws Exception {

        PostArticleResponse response = null;

        Channel _channel = getChannel();

        if ((_channel != null) && (_channel.isActive())) {
            _channel.writeAndFlush(request);
            SimpleMessage simpleMessage = getMessageAndCheck(request.getUsn());

            response = (PostArticleResponse) simpleMessage;

        } else {
            throw new Exception("Client is disconnected.");
        }

        return response;
    }    

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleMessage msg) throws Exception {
        LOGGER.debug("Message received: {}", msg);
        messagesReceived.add(msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        setChannel(ctx.channel());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
