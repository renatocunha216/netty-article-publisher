package br.com.rbcti.publisher.common.messages;

/**
 * Interface that all messages in this project must implement.
 * 
 * @author Renato Cunha
 * @version 1.0
 */
public interface SimpleMessage {
    
    /**
     * 
     * @return
     */
    public abstract int getMessageId();
    
    /**
     * 
     * @return
     */
    public abstract int getVersion();

    /**
     * 
     * @return
     */
   public abstract long getUsn();
   
   /**
    * 
    * @return
    */
   public byte[] getData();

}
