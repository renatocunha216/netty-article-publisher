package br.com.rbcti.publisher.common.messages;

import com.google.flatbuffers.FlatBufferBuilder;

import br.com.rbcti.publisher.common.messages.fbs.AllProtocolMessagesFbs;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.PostArticleResponseFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class PostArticleResponse implements SimpleMessage {
    
    private int messageId;
    private int version;
    private long usn;
    private byte responseCode;
    private String articleUuid;
    
    public PostArticleResponse() {
        this.messageId = Messages.POST_ARTICLE_RESPONSE;
        this.version = 1;
    }
    
    public PostArticleResponse(long usn, byte responseCode, String articleUuid) {
        this();
        this.usn = usn;
        this.responseCode = responseCode;        
        this.articleUuid = articleUuid;
    }    

    public byte getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(byte responseCode) {
        this.responseCode = responseCode;
    }

    public String getArticleUuid() {
        return articleUuid;
    }

    public void setArticleUuid(String articleUuid) {
        this.articleUuid = articleUuid;
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

    @Override
    public byte[] getData() {
        
        FlatBufferBuilder builder = new FlatBufferBuilder();
        
        int headerOffset = HeaderFbs.createHeaderFbs(builder, AllProtocolMessagesFbs.postArticleResponseFbs, getVersion(), getUsn());
        int articleUuidOffset = builder.createString(getArticleUuid());
        
        int postArticleResponseOffset = PostArticleResponseFbs.createPostArticleResponseFbs(builder, headerOffset, getResponseCode(), articleUuidOffset);

        ProtocolMessageFbs.startProtocolMessageFbs(builder);
        ProtocolMessageFbs.addMessage(builder, postArticleResponseOffset);
        ProtocolMessageFbs.addMessageType(builder, AllProtocolMessagesFbs.postArticleResponseFbs);
        int endOffset = ProtocolMessageFbs.endProtocolMessageFbs(builder);
        ProtocolMessageFbs.finishProtocolMessageFbsBuffer(builder, endOffset);
        
        return builder.sizedByteArray();         
    }

    @Override
    public String toString() {
        return "PostArticleResponse [messageId=" + messageId + ", version=" + version + ", usn=" + usn + ", responseCode=" + responseCode + ", articleUuid=" + articleUuid + "]";
    }   

}
