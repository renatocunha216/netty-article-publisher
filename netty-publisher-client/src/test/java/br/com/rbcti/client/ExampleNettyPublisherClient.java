package br.com.rbcti.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.LoginResponse;

/**
 *
 * @author Renato Cunha
 *
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

            LoginResponse result = client.loginRequest(login);

            LOGGER.info("::{}", result);

            if (LoginResponse.LOGIN_OK == result.getResponseCode()) {
                LOGGER.info("Successful login! ", result);

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
