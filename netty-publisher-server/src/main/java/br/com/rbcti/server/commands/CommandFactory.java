package br.com.rbcti.server.commands;

import java.util.HashMap;
import java.util.Map;

import br.com.rbcti.publisher.common.commands.Command;
import br.com.rbcti.publisher.common.messages.Messages;

/**
 *
 * @author Renato Cunha
 *
 */
public class CommandFactory {

    private static Map<Integer, Command>registeredCommands;

    static {
        registeredCommands = new HashMap<Integer, Command>();
        
        registeredCommands.put(Integer.valueOf(Messages.LOGIN_REQUEST), new LoginCommand());
        registeredCommands.put(Integer.valueOf(Messages.POST_ARTICLE_REQUEST), new PostArticleCommand());
        
    }

    public static Command getCommand(int messageId) {
        return registeredCommands.get(Integer.valueOf(messageId));
    }

}