package com.cqs.nio.netty.helloWorld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by cqs on 17-1-3.
 */
public class ClientDemo {

    //
    private void start() {
        EventLoopGroup workLoop = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workLoop)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectClientHandler());
                        }
                    });
            try {
                bootstrap.connect("127.0.0.1", 8086).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        } finally {
            System.out.println("你倒是关闭啊");
//            workLoop.shutdownGracefully();
        }
    }

    public static void main(String[] args)  {
        ClientDemo demo = new ClientDemo();
        demo.start();
    }
}
