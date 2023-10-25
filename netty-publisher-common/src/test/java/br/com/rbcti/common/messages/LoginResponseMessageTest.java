package br.com.rbcti.common.messages;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.testng.annotations.Test;

import br.com.rbcti.publisher.common.messages.LoginResponse;
import br.com.rbcti.publisher.common.messages.Messages;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginResponseFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

public class LoginResponseMessageTest {
    
    @Test
    public void testEncodeDecode() {

        System.out.println(getClass().getSimpleName() + ".testEncodeDecode");
        
        final int MESSAGE_VERSION = 1;
        
        Random randomUSN = new Random();
        Random randomResponseConde = new Random();
        
        for (int c = 0; c < 10_000; c++) {
            
            UUID uuid = UUID.randomUUID();
            long usn = randomUSN.nextLong();
            byte responseCode = (byte) randomResponseConde.nextInt(); 
            
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsn(usn);
            loginResponse.setResponseCode(responseCode);
            loginResponse.setAuthenticationToken(uuid.toString());

            byte[] flatBufferData = loginResponse.getData();            
            
            ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(ByteBuffer.wrap(flatBufferData));
            LoginResponseFbs response = (LoginResponseFbs) messageProtocol.message(new LoginResponseFbs());
            HeaderFbs header = response.header();
            
            assertEquals(header.messageId(), Messages.LOGIN_RESPONSE);
            assertEquals(header.version(), MESSAGE_VERSION);
            assertEquals(header.usn(), usn);
            assertEquals(response.responseCode(), responseCode);
            assertEquals(response.authenticationToken(), uuid.toString());
        }

    }     

}
