package br.com.rbcti.publisher.common.messages;

import java.util.ArrayList;
import java.util.List;

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
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String toString() {
        return "PostArticleRequest [messageId=" + messageId + ", version=" + version + ", usn=" + usn + ", authenticationToken=" + authenticationToken + ", subject=" + subject + ", article=" + article
                + ", tags=" + tags + "]";
    }    

}
