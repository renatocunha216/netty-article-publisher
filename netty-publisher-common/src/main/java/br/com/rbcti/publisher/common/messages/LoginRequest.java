package br.com.rbcti.publisher.common.messages;

import com.google.flatbuffers.FlatBufferBuilder;

import br.com.rbcti.publisher.common.messages.fbs.AllProtocolMessagesFbs;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class LoginRequest implements SimpleMessage {
    
    private int messageId;
    private int version;
    private long usn;
    private String userName;
    private String password;
    
    public LoginRequest() {
        this.messageId = Messages.LOGIN_REQUEST;
        this.version = 1;
    }    
    
    public LoginRequest(String userName, String password, long usn) {
        this();
        this.userName = userName;
        this.password = password;
        this.usn = usn;
    }   

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUsn(long usn) {
        this.usn = usn;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    

    @Override
    public int getMessageId() {
        return messageId;
    }

    @Override
    public int getVersion() {
        return version;
    }
    
    @Override
    public long getUsn() {
        return usn;
    }
    
    @Override
    public byte[] getData() {
        
        FlatBufferBuilder builder = new FlatBufferBuilder();
        
        int headerOffset = HeaderFbs.createHeaderFbs(builder, AllProtocolMessagesFbs.loginRequest, getVersion(), getUsn());
        int userNameOffset = builder.createString(getUserName());
        int passwordOffset = builder.createString(getPassword());
        int loginOffset = LoginRequestFbs.createLoginRequestFbs(builder, headerOffset, userNameOffset, passwordOffset);

        ProtocolMessageFbs.startProtocolMessageFbs(builder);
        ProtocolMessageFbs.addMessage(builder, loginOffset);
        ProtocolMessageFbs.addMessageType(builder, AllProtocolMessagesFbs.loginRequest);
        int endOffset = ProtocolMessageFbs.endProtocolMessageFbs(builder);
        ProtocolMessageFbs.finishProtocolMessageFbsBuffer(builder, endOffset);
        
        return builder.sizedByteArray();
    }

    @Override
    public String toString() {
        return "LoginRequest [messageId=" + messageId + ", version=" + version + ", usn=" + usn + ", userName=" + userName + ", password=" + password + "]";
    }   

}
