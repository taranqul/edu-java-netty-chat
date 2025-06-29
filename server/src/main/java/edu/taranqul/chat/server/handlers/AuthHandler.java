package edu.taranqul.chat.server.handlers;

import edu.taranqul.chat.server.services.UserService;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;

public class AuthHandler extends SimpleChannelInboundHandler<String> {
    private final UserService userService;
    private final ChannelGroup channels;

    public AuthHandler(UserService userService, ChannelGroup channels) {
        this.userService = userService;
        this.channels = channels;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        String[] parts = msg.trim().split("\\s+");

        if (parts.length != 3) {
            ctx.writeAndFlush("INVALID FORMAT. Use LOGIN or REGISTER: <username> <password>\n");
            return;
        }

        String command = parts[0].toUpperCase();
        String username = parts[1];
        String password = parts[2];

        switch (command) {
            case "LOGIN" -> {
                if (userService.checkCredentials(username, password)) {
                    ctx.writeAndFlush("VALID\n");
                    channels.add(ctx.channel());
                    ctx.pipeline().replace(this, "chat", new ChatHandler(channels, username));
                    System.out.println("Auth success: " + username);
                } else {
                    ctx.writeAndFlush("REJECT\n").addListener(ChannelFutureListener.CLOSE);
                    System.out.println("Auth failed: " + username);
                }
            }

            case "REGISTER" -> {
                if (userService.register(username, password)) {
                    ctx.writeAndFlush("REGISTERED\n");
                    channels.add(ctx.channel());
                    ctx.pipeline().replace(this, "chat", new ChatHandler(channels, username));
                    System.out.println("Registered and logged in: " + username);
                } else {
                    ctx.writeAndFlush("ALREADY_EXISTS\n");
                    System.out.println("Register failed — exists: " + username);
                }
            }

            default -> ctx.writeAndFlush("UNKNOWN COMMAND. Use LOGIN or REGISTER.\n");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
