package br.com.rbcti.server.commands;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.rbcti.publisher.common.commands.Command;
import br.com.rbcti.publisher.common.messages.PostArticleRequest;
import br.com.rbcti.publisher.common.messages.PostArticleResponse;
import br.com.rbcti.publisher.common.messages.SimpleMessage;
import io.netty.channel.ChannelHandlerContext;

public class PostArticleCommand implements Command {
    
    private static final Logger LOGGER = LogManager.getLogger(PostArticleCommand.class);

    @Override
    public void execute(ChannelHandlerContext ctx, SimpleMessage message) {
        
        PostArticleRequest postArticleRequest = (PostArticleRequest) message;
        
        String token =  postArticleRequest.getAuthenticationToken();
        String subject =  postArticleRequest.getSubject();
        String article = postArticleRequest.getArticle();
        List<String> tags = postArticleRequest.getTags();
        
        StringJoiner joiner = new StringJoiner(", ");
        tags.forEach(tag -> joiner.add(tag));
        
        LOGGER.info("Token: {}", token);
        LOGGER.info("Subject: {}", subject);
        LOGGER.info("Article: {}", article);
        LOGGER.info("Tags: {}", joiner.toString());
        
        PostArticleResponse response = new PostArticleResponse(postArticleRequest.getUsn(), (byte) 1, UUID.randomUUID().toString());

        ctx.writeAndFlush(response);        
    }

}
