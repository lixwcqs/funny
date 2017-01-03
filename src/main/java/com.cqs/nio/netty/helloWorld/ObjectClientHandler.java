package com.cqs.nio.netty.helloWorld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 17-1-3.
 */
public class ObjectClientHandler extends ChannelInboundHandlerAdapter {
    private Random random = new Random();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        System.out.println(ctx.getClass());
        exec.scheduleAtFixedRate(() -> {
            try {
                ctx.writeAndFlush("rand:\t" + random.nextInt(100));
            } catch (Throwable e) {
                exec.shutdown();
                System.out.println("是否关闭ScheduledExecutorService:\t"+exec.isShutdown());
            }
        }, 0, 1, TimeUnit.SECONDS);

//        TimeUnit.SECONDS.sleep(5);
//        exec.shutdown();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client 读取消息:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
