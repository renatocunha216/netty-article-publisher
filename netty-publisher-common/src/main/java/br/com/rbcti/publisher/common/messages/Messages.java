package br.com.rbcti.publisher.common.messages;

/**
 *
 *
 * @author Renato Cunha
 *
 */
public final class Messages {
    
    public static final int LOGIN_REQUEST = 0x0005;    
    public static final int LOGIN_RESPONSE = 0x0006;
    
    public static final int POST_ARTICLE_REQUEST = 0x0007;
    public static final int POST_ARTICLE_RESPONSE = 0x0008;
    
    // Private constructor to prevent objects of this class from being created
    private Messages() {
        throw new AssertionError();
    }

}