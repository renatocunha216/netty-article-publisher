package br.com.rbcti.server.commands;

import br.com.rbcti.publisher.common.commands.Command;
import br.com.rbcti.publisher.common.messages.LoginRequest;
import br.com.rbcti.publisher.common.messages.LoginResponse;
import br.com.rbcti.publisher.common.messages.SimpleMessage;
import io.netty.channel.ChannelHandlerContext;;

/**
 *
 * @author Renato Cunha
 *
 */
public class LoginCommand implements Command {

    @Override
    public void execute(ChannelHandlerContext ctx, SimpleMessage message) {

        LoginRequest loginMessage = (LoginRequest) message;
        
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        
        LoginResponse response = new LoginResponse(token, (byte) 1, loginMessage.getUsn());

        ctx.writeAndFlush(response);
    }
}
