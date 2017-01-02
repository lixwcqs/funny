/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.cqs.nio.netty.example.objectecho;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the client.
        System.out.println("接受数据" + msg);
        try {
            ctx.write(msg);
            try {
                System.out.println(1/0);
            } catch (Exception e) {
                System.err.println("异常"+ e.getMessage());
            }
        } finally {
            ctx.close();
        }
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("读取数据完毕");
        ctx.flush();
    }

    //可以由channelRead方法抛出的异常(若是在channelRead已经处理了[try{}catch{}不执行])触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        System.out.println("捕捉到异常了");
    }
}
