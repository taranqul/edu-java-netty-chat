// File: EchoServerHandler.java
package edu.taranqul.chat.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Received: " + msg);
        ctx.writeAndFlush("Echo: " + msg);
    }
}

