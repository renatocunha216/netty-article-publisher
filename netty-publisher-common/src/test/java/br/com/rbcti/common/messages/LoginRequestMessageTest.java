package br.com.rbcti.common.messages;

import static org.testng.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Random;

import org.testng.annotations.Test;

import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.Messages;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

public class LoginRequestMessageTest {
    
    @Test
    public void testEncodeDecode() {

        System.out.println(getClass().getSimpleName() + ".testEncodeDecode");
        
        final int MESSAGE_VERSION = 1;
        
        Random random = new Random();

        String userName = "User_Name_";
        String password = "Password#556677$";
        
        for (int c = 0; c < 10_000; c++) {
            
            long usn = random.nextLong();
            
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUserName(userName + usn);
            loginRequest.setPassword(password + usn);
            loginRequest.setUsn(usn);

            byte[] flatBufferData = loginRequest.getData();

            ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(ByteBuffer.wrap(flatBufferData));
            LoginRequestFbs login = (LoginRequestFbs) messageProtocol.message(new LoginRequestFbs());
            HeaderFbs header = login.header();

            assertEquals(header.messageId(), Messages.LOGIN_REQUEST);
            assertEquals(header.version(), MESSAGE_VERSION);
            assertEquals(header.usn(), usn);
            assertEquals(login.userName(), userName + usn);
            assertEquals(login.password(), password + usn);          
        }

    }    

}
