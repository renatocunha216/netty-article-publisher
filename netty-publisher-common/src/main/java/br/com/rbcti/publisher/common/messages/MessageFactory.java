package br.com.rbcti.publisher.common.messages;

import java.nio.ByteBuffer;

import br.com.rbcti.publisher.common.messages.fbs.AllProtocolMessagesFbs;
import br.com.rbcti.publisher.common.messages.fbs.HeaderFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.LoginResponseFbs;
import br.com.rbcti.publisher.common.messages.fbs.PostArticleRequestFbs;
import br.com.rbcti.publisher.common.messages.fbs.ProtocolMessageFbs;

/**
 * 
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public class MessageFactory {

    private MessageFactory() {
        
    }

    public static SimpleMessage getMessageInstance(ByteBuffer flatBufferData) {
        
        SimpleMessage message = null;
        
        ProtocolMessageFbs messageProtocol = ProtocolMessageFbs.getRootAsProtocolMessageFbs(flatBufferData);

        switch (messageProtocol.messageType()) {
        
            case AllProtocolMessagesFbs.loginRequestFbs:
                message = decodeLoginRequest(messageProtocol);
                break;
    
            case AllProtocolMessagesFbs.loginResponseFbs:
                message = decodeLoginResponse(messageProtocol);
                break;
    
            case AllProtocolMessagesFbs.postArticleRequestFbs:
                message = decodePostArticleRequest(messageProtocol);
                break;
    
            case AllProtocolMessagesFbs.postArticleResponseFbs:
                break;
    
            default:
                throw new IllegalArgumentException("Unknown message.");                
        }          

        return message;
    }

    public static SimpleMessage getMessageInstance(byte [] flatBufferData) {
        return getMessageInstance(ByteBuffer.wrap(flatBufferData));
    }
    
    private static LoginRequest decodeLoginRequest(ProtocolMessageFbs _messageProtocol) {        
        LoginRequestFbs login = (LoginRequestFbs) _messageProtocol.message(new LoginRequestFbs());
        HeaderFbs header = login.header();
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMessageId(header.messageId());
        loginRequest.setVersion(header.version());
        loginRequest.setUsn(header.usn());
        loginRequest.setUserName(login.userName());
        loginRequest.setPassword(login.password());
        
        return loginRequest;        
    }
    
    private static LoginResponse decodeLoginResponse(ProtocolMessageFbs _messageProtocol) {        
        LoginResponseFbs response = (LoginResponseFbs) _messageProtocol.message(new LoginResponseFbs());
        HeaderFbs header = response.header();
        
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessageId(header.messageId());
        loginResponse.setVersion(header.version());
        loginResponse.setUsn(header.usn());
        loginResponse.setAuthenticationToken(response.authenticationToken());
        loginResponse.setResponseCode(response.responseCode());
        
        return loginResponse;        
    }    
    
    private static PostArticleRequest decodePostArticleRequest(ProtocolMessageFbs _messageProtocol) {
        
        PostArticleRequestFbs postArticle = (PostArticleRequestFbs) _messageProtocol.message(new PostArticleRequestFbs());
        HeaderFbs header = postArticle.header();
        
        PostArticleRequest postArticleRequest = new PostArticleRequest();
        postArticleRequest.setMessageId(header.messageId());
        postArticleRequest.setVersion(header.version());
        postArticleRequest.setUsn(header.usn());
        
        postArticleRequest.setAuthenticationToken(postArticle.authenticationToken());
        postArticleRequest.setSubject(postArticle.subject());
        postArticleRequest.setArticle(postArticle.article());
        
        final int tagsLength = postArticle.tagsLength(); 
        
        if (tagsLength > 0) {
            for (int c = 0; c < tagsLength; c++) {
                postArticleRequest.addTags(postArticle.tags(c));                                
            }
        }
        
        return postArticleRequest;        
    }

}
