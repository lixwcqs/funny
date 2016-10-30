package com.cqs.socket.example.demo_nio;

import com.cqs.socket.example.demo2.SerializableUtil;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
public class SocketServerNIO {


    public static void main(String[] args) {
        new SocketServerNIO().run();
    }


    public void run() {
        Selector selector = null;
        ServerSocketChannel channel = null;
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(10005));
            channel.socket().setReuseAddress(true);//
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
                            e.printStackTrace();
                        }
                        Film film = readFilm(socketChannel);
                        System.out.println("filmInfo:\t" + film);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Film readFilm(SocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while (channel.read(buffer) != -1) {
                buffer.flip();
                baos.write(buffer.array());
                buffer.clear();
            }

            byte[] bytes = baos.toByteArray();
            return SerializableUtils.toFilm(bytes);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(channel);
        }
    }
}
