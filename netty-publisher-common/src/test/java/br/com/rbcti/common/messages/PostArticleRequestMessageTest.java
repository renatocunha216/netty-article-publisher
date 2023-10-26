package br.com.rbcti.common.messages;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.testng.annotations.Test;

import br.com.rbcti.publisher.common.messages.Messages;
import br.com.rbcti.publisher.common.messages.PostArticleRequest;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.PostArticleRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

public class PostArticleRequestMessageTest {

    private String[] tags = { "java", "html", "css", "sass", "javascript", "c#", "c/c++", "python", "go lang", "php",
            "pearl", "lua", "fortran", "windows", "linux", "oracle", "mysql", "postgresql", "angular", "react", "vue.js" };

    @Test
    public void testEncodeDecode() {

        System.out.println(getClass().getSimpleName() + ".testEncodeDecode");
        
        final int MESSAGE_VERSION = 1;
        
        String subject = "subject#";
        String article = "article$";

        Random randomUSN = new Random();
        
        int[] tagIndex = new int[tags.length];

        for (int c = 0; c < 10_000; c++) {
            
            UUID uuid = UUID.randomUUID();
            long usn = randomUSN.nextLong();
            
            PostArticleRequest postArticleRequest = new PostArticleRequest();
            postArticleRequest.setUsn(usn);            
            postArticleRequest.setAuthenticationToken(uuid.toString());
            postArticleRequest.setSubject(subject + usn);
            postArticleRequest.setArticle(article + usn);
            
            for (int x = 0; x < tagIndex.length; x++) {
                tagIndex[x] = (int) (Math.random() * (tagIndex.length));
                postArticleRequest.addTag(tags[tagIndex[x]]);                
            }
            
            byte[] flatBufferData = postArticleRequest.getData();

            ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(ByteBuffer.wrap(flatBufferData));
            PostArticleRequestFbs postArticle = (PostArticleRequestFbs) messageProtocol.message(new PostArticleRequestFbs());
            HeaderFbs header = postArticle.header();
            
            assertEquals(header.messageId(), Messages.POST_ARTICLE_REQUEST);
            assertEquals(header.version(), MESSAGE_VERSION);
            assertEquals(header.usn(), usn);
            
            assertEquals(postArticle.authenticationToken(), uuid.toString());
            assertEquals(postArticle.subject(), subject + usn);
            assertEquals(postArticle.article(), article + usn);
            
            final int tagsLength = postArticle.tagsLength();
            
            if (tagsLength > 0) {
                for (int x = 0; x < tagsLength; x++) {
                    assertEquals(postArticle.tags(x), tags[tagIndex[x]]);
                }
            }
        }

    }
    
    @Test
    public void testEncodeDecodeWithoutTags() {

        System.out.println(getClass().getSimpleName() + ".testEncodeDecodeWithoutTags");
        
        final int MESSAGE_VERSION = 1;
        
        String subject = "subject#";
        String article = "article$";

        Random randomUSN = new Random();
        
        int[] tagIndex = new int[tags.length];

        for (int c = 0; c < 10_000; c++) {
            
            UUID uuid = UUID.randomUUID();
            long usn = randomUSN.nextLong();
            
            PostArticleRequest postArticleRequest = new PostArticleRequest();
            postArticleRequest.setUsn(usn);            
            postArticleRequest.setAuthenticationToken(uuid.toString());
            postArticleRequest.setSubject(subject + usn);
            postArticleRequest.setArticle(article + usn);
            
            byte[] flatBufferData = postArticleRequest.getData();

            ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(ByteBuffer.wrap(flatBufferData));
            PostArticleRequestFbs postArticle = (PostArticleRequestFbs) messageProtocol.message(new PostArticleRequestFbs());
            HeaderFbs header = postArticle.header();
            
            assertEquals(header.messageId(), Messages.POST_ARTICLE_REQUEST);
            assertEquals(header.version(), MESSAGE_VERSION);
            assertEquals(header.usn(), usn);
            
            assertEquals(postArticle.authenticationToken(), uuid.toString());
            assertEquals(postArticle.subject(), subject + usn);
            assertEquals(postArticle.article(), article + usn);
            
            final int tagsLength = postArticle.tagsLength();
            
            if (tagsLength > 0) {
                for (int x = 0; x < tagsLength; x++) {
                    assertEquals(postArticle.tags(x), tags[tagIndex[x]]);
                }
            }
        }
    }     

}
