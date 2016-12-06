package com.cqs.socket.example.file;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by cqs on 10/30/16.
 */
public class SocketServerFile {

    static int count = 0;

    public static void run(){
        Selector selector = null;
        ServerSocketChannel channel = null;
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(10007));
            channel.socket().setReuseAddress(true);
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                keys.forEachRemaining(key -> {
                    keys.remove();
                    if (key.channel() instanceof ServerSocketChannel) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = null;
                        try {
                            socketChannel = serverSocketChannel.accept();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        receiveFileByChannel(socketChannel);
                    }
                });
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void receiveFileByChannel(SocketChannel socketChannel) {
        OutputStream os = null;
        try {
            os = new FileOutputStream("/home/cqs/Downloads/funny/algs4-master" + (++count) + ".zip");
            final int size = 1024 * 1024;
            byte[] bytes = new byte[size];
            ByteBuffer buffer = ByteBuffer.allocate(size);
            int len;
            while ((len = socketChannel.read(buffer)) != -1) {
                buffer.flip();
                buffer.get(bytes, 0, len);
                buffer.clear();
                os.write(bytes);
            }
            os.flush();
            System.out.println("成功接收第 " + count + "个文件");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    public static void main(String[] args) {
        run();
    }
}
