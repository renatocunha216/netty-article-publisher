package br.com.rbcti.common.messages;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.testng.annotations.Test;

import br.com.rbcti.publisher.common.messages.Messages;
import br.com.rbcti.publisher.common.messages.PostArticleResponse;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.PostArticleResponseFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class PostArticleResponseMessageTest {
    
    @Test
    public void testEncodeDecode() {

        System.out.println(getClass().getSimpleName() + ".testEncodeDecode");
        
        final int MESSAGE_VERSION = 1;
        
        Random randomUSN = new Random();
        
        short responseCode = Byte.MIN_VALUE;

        for (int c = 0; c < 10_000; c++) {
            
            UUID uuid = UUID.randomUUID();
            long usn = randomUSN.nextLong();
            
            PostArticleResponse postArticleResponse = new PostArticleResponse();
            postArticleResponse.setUsn(usn);            
            postArticleResponse.setResponseCode((byte) responseCode);
            postArticleResponse.setArticleUuid(uuid.toString());
            
            byte[] flatBufferData = postArticleResponse.getData();

            ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(ByteBuffer.wrap(flatBufferData));
            PostArticleResponseFbs postResponseArticle = (PostArticleResponseFbs) messageProtocol.message(new PostArticleResponseFbs());
            HeaderFbs header = postResponseArticle.header();
            
            assertEquals(header.messageId(), Messages.POST_ARTICLE_RESPONSE);
            assertEquals(header.version(), MESSAGE_VERSION);
            assertEquals(header.usn(), usn);
            
            assertEquals(postResponseArticle.articleUuid(), uuid.toString());
            assertEquals(postResponseArticle.responseCode(), (byte) responseCode);
            
            responseCode++;
            
            if (responseCode > Byte.MAX_VALUE) {
                responseCode = Byte.MIN_VALUE;                
            }            
        }        
    }    

}
