package br.com.rbcti.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.LoginResponse;
import br.com.rbcti.publisher.common.messages.PostArticleRequest;
import br.com.rbcti.publisher.common.messages.PostArticleResponse;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class ExampleNettyPublisherClient {

    private static final Logger LOGGER = LogManager.getLogger(ExampleNettyPublisherClient.class);
    private static long usn = 6643;

    public static void main(String[] args) {

        NettyPublisherClientTest client = new NettyPublisherClientTest("127.0.0.1", 10079);

        try {
            client.start();
            LOGGER.info("Connected.");

            LoginRequest login = new LoginRequest("user1", "password#123", usn++);

            LoginResponse result = client.login(login);

            LOGGER.info("::{}", result);

            if (LoginResponse.LOGIN_OK == result.getResponseCode()) {
                LOGGER.info("Successful login! ", result);
                
                // publish an article
                PostArticleRequest article1 = new PostArticleRequest();
                article1.setAuthenticationToken(result.getAuthenticationToken());
                article1.setSubject("Software Engineering");
                article1.setArticle("Article content.");
                article1.setUsn(usn++);
                article1.addTags("java").addTags("netty").addTags("flatbuffers");
                
                PostArticleResponse respose1 = client.postArticle(article1);
                
                LOGGER.info("::{}", respose1);
                
                // publish another article
                PostArticleRequest article2 = new PostArticleRequest();
                article2.setAuthenticationToken(result.getAuthenticationToken());
                article2.setSubject("Health");
                article2.setArticle("Article content.");
                article2.setUsn(usn++);
                article2.addTags("food").addTags("gym").addTags("fitness");
                article2.addTags("medicine").addTags("stress").addTags("obesity");
                
                PostArticleResponse respose2 = client.postArticle(article2);
                
                LOGGER.info("::{}", respose2);
                

            } else {
                LOGGER.info("Login failed.", result);
            }

            Thread.sleep(1000);

        } catch(Exception e) {
            e.printStackTrace();

        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Finished example application.");
    }   

}
