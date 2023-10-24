package br.com.rbcti.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.client.handlers.ClientHandler;
import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.LoginResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class NettyPublisherClientTest {

    private static final Logger LOGGER = LogManager.getLogger(NettyPublisherClientTest.class);

    private final int port;
    private String host;
    private Channel channel;
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;

    public NettyPublisherClientTest(String host) {
        this.host = host;
        this.port = 10079;
    }

    public NettyPublisherClientTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public synchronized void start() throws Exception {

        workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        //bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        bootstrap.handler(new ClientChannelInitializer());

        ChannelFuture future = bootstrap.connect(host, port).sync();

        future.awaitUninterruptibly(5000);

        if (!future.isSuccess()) {
          throw new Exception(future.cause());
        }
        setChannel(future.channel());
        // Wait until the connection is closed.
        //future.channel().closeFuture().sync();
    }

    public boolean isActive() {
        return channel.isActive();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public synchronized void stop() {
        if (channel.isOpen()) {
            channel.close().awaitUninterruptibly();
        }
        bootstrap.clone();
        workerGroup.shutdownGracefully().awaitUninterruptibly(5000);
    }

    public LoginResponse loginRequest(LoginRequest request) throws Exception {

        long startTime = System.currentTimeMillis();
        ClientHandler clientHandler = (ClientHandler) getChannel().pipeline().get("clientHandler");
        LoginResponse response = clientHandler.loginRequest(request);
        long endTime = System.currentTimeMillis();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{} message processing time {} ms", LoginRequest.class.getSimpleName(), Long.valueOf(endTime - startTime));
        }
        return response;
    }
    
}
