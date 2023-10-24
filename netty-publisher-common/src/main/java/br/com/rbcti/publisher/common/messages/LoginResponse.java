package br.com.rbcti.publisher.common.messages;

import com.google.flatbuffers.FlatBufferBuilder;

import br.com.rbcti.publisher.common.messages.fbs.AllProtocolMessagesFbs;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginResponseFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

public class LoginResponse implements SimpleMessage {
    
    public static final int LOGIN_OK = 1;
    
    private int messageId;
    private int version;
    private long usn;
    private byte responseCode;
    private String authenticationToken;
    
    public LoginResponse() {
        this.messageId = Messages.LOGIN_RESPONSE;
        this.version = 1;
    }
    
    public LoginResponse(String authenticationToken, byte responseCode, long usn) {
        this();
        this.authenticationToken = authenticationToken;
        this.responseCode = responseCode;
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

    @Override
    public int getMessageId() {        
        return this.messageId;
    }

    @Override
    public int getVersion() {        
        return this.version;
    }

    @Override
    public long getUsn() {        
        return this.usn;
    }
    
    public byte getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(byte responseCode) {
        this.responseCode = responseCode;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
    
    @Override
    public byte[] getData() {
        
        FlatBufferBuilder builder = new FlatBufferBuilder();
        
        int headerOffset = HeaderFbs.createHeaderFbs(builder, AllProtocolMessagesFbs.loginResponseFbs, getVersion(), getUsn());
        int tokenOffset = builder.createString(getAuthenticationToken());
        
        int loginResponseOffset = LoginResponseFbs.createLoginResponseFbs(builder, headerOffset, getResponseCode(), tokenOffset);

        ProtocolMessageFbs.startProtocolMessageFbs(builder);
        ProtocolMessageFbs.addMessage(builder, loginResponseOffset);
        ProtocolMessageFbs.addMessageType(builder, AllProtocolMessagesFbs.loginResponseFbs);
        int endOffset = ProtocolMessageFbs.endProtocolMessageFbs(builder);
        ProtocolMessageFbs.finishProtocolMessageFbsBuffer(builder, endOffset);
        
        return builder.sizedByteArray();        
    }    

    @Override
    public String toString() {
        return "LoginResponse [messageId=" + messageId + ", version=" + version + ", usn=" + usn + ", responseCode=" + responseCode + ", authenticationToken=" + authenticationToken + "]";
    }

}
