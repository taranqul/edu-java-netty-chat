// File: EchoServer.java
package edu.taranqul.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


import edu.taranqul.chat.server.handlers.AuthHandler;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    private final int port;
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final Dependencies deps;

    public Server(int port, Dependencies dependencies) {
        this.deps = dependencies;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup boss = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
        EventLoopGroup worker = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<Channel>() {
                         @Override
                         protected void initChannel(Channel ch) {
                             ChannelPipeline pipeline = ch.pipeline();
                             pipeline.addLast("decoder", new StringDecoder());
                             pipeline.addLast("encoder", new StringEncoder());
                             pipeline.addLast("validator", new AuthHandler(deps.getUserService(), channels));
                         }
                     });

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Server started on port: "+ port);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
