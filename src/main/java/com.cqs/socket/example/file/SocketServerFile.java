package com.cqs.socket.example.file;

import org.apache.commons.io.IOUtils;

import java.io.*;
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

    public static void receiveFile() {
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
                        OutputStream os = null;
                        try {
                            socketChannel = serverSocketChannel.accept();
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
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            IOUtils.closeQuietly(os);
//                            IOUtils.closeQuietly(socketChannel);
//                            IOUtils.closeQuietly(serverSocketChannel);
                            System.out.println("处理" + count + "个Socket链接完毕");
                        }
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        receiveFile();
    }
}
