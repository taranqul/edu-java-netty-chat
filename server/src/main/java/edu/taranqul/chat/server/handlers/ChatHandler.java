package edu.taranqul.chat.server.handlers;

import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.ChannelHandlerContext;

public class ChatHandler extends SimpleChannelInboundHandler<String> {
    private final ChannelGroup channels;
    private final String username;

    public ChatHandler(ChannelGroup channels, String username) {
        this.channels = channels;
        this.username = username;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        channels.writeAndFlush("Message from:" + username + "\nmessage:\n" + msg);
        System.out.println("From:" + username + ": " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
