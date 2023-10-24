package br.com.rbcti.publisher.common.encoders;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.messages.SimpleMessage;
import br.com.rbcti.publisher.common.util.ByteBufferWorker;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author Renato Cunha
 *
 */
@Sharable
public class EncoderMessage extends MessageToByteEncoder<SimpleMessage> {

    private static final Logger LOGGER = LogManager.getLogger(EncoderMessage.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, SimpleMessage msg, ByteBuf out) throws Exception {
        
        byte[] messageData = msg.getData();
        
        ByteBuffer frameBuffer = ByteBuffer.allocate(2 + messageData.length); // Length + data
        ByteBufferWorker.putUnsignedShort(frameBuffer, messageData.length);
        frameBuffer.put(messageData);
        
        byte[] frameBufferRaw = frameBuffer.array();

        if (LOGGER.isDebugEnabled()) {
            synchronized (EncoderMessage.class) {
                LOGGER.debug("Sending message: {}", msg);
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Sending data: {}", ByteBufferWorker.getDumpString(ByteBuffer.wrap(frameBufferRaw)));
                }
            }
        }

        out.writeBytes(frameBufferRaw);
    }
}
