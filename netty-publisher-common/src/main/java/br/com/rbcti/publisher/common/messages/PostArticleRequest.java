package br.com.rbcti.publisher.common.messages;

import java.util.ArrayList;
import java.util.List;

import com.google.flatbuffers.FlatBufferBuilder;

import br.com.rbcti.publisher.common.messages.fbs.AllProtocolMessagesFbs;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.PostArticleRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class PostArticleRequest implements SimpleMessage {

    private int messageId;
    private int version;
    private long usn;
    private String authenticationToken;
    private String subject;
    private String article;
    private List<String> tags;
    
    public PostArticleRequest() {
        this.tags = new ArrayList<String>();
        this.messageId = Messages.POST_ARTICLE_REQUEST;
        this.version = 1;
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

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTags(String tag) {
        this.tags.add(tag);
    }

    @Override
    public byte[] getData() {
        
        FlatBufferBuilder builder = new FlatBufferBuilder();
        
        int headerOffset = HeaderFbs.createHeaderFbs(builder, AllProtocolMessagesFbs.postArticleRequestFbs, getVersion(), getUsn());
        int authenticationTokenOffset = builder.createString(getAuthenticationToken());
        int subjectOffset = builder.createString(getSubject());
        int articleOffset = builder.createString(getArticle());
        int tagsVectorOffset = 0;
        
        if ((this.tags != null) && (this.tags.size() > 0)) {
            int[] tagsOffsets = new int[this.tags.size()];
            int i = 0;
            for (String tag : tags) {
                tagsOffsets[i] = builder.createString(tag);
                i++;
            }
            
            tagsVectorOffset = PostArticleRequestFbs.createTagsVector(builder, tagsOffsets);
        }
        
        int postArticleOffset = PostArticleRequestFbs.createPostArticleRequestFbs(builder, headerOffset, authenticationTokenOffset, subjectOffset, articleOffset, tagsVectorOffset);
        
        ProtocolMessageFbs.startProtocolMessageFbs(builder);
        ProtocolMessageFbs.addMessage(builder, postArticleOffset);
        ProtocolMessageFbs.addMessageType(builder, AllProtocolMessagesFbs.postArticleRequestFbs);
        int endOffset = ProtocolMessageFbs.endProtocolMessageFbs(builder);
        ProtocolMessageFbs.finishProtocolMessageFbsBuffer(builder, endOffset);      
        
        return builder.sizedByteArray();
    }
    
    @Override
    public String toString() {
        return "PostArticleRequest [messageId=" + messageId + ", version=" + version + ", usn=" + usn + ", authenticationToken=" + authenticationToken + ", subject=" + subject + ", article=" + article
                + ", tags=" + tags + "]";
    }    

}
