package com.cqs.socket.example.alive;

import com.cqs.socket.example.demo_nio.Film;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by cqs on 16-12-31.
 */
public class ServerSocketAlive {

    public static void main(String[] args) throws IOException {
        ServerSocketAlive serverSocketAlive = new ServerSocketAlive();
        serverSocketAlive.run();
    }

    private void run() throws IOException {
        //新建新的selector
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        System.out.println("socketChannel hashCode:\t" + socketChannel.hashCode());
        socketChannel.socket().bind(new InetSocketAddress(9874));
        socketChannel.socket().setReuseAddress(true);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        int loop = 0;
        while (true) {
            System.out.println("loop:" + ++loop);
            int readyChannels = selector.select();//一直阻塞
            System.out.println("readyChannels:" + readyChannels);
            if (readyChannels == 0) continue;

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            iterator.forEachRemaining(selectionKey -> {
                printKeyStatus(selectionKey);
                handler(selectionKey, selector);
                iterator.remove();
            });
        }
    }

    private void handler(SelectionKey key, Selector selector) {
        int type = getKeyType(key);
        System.out.println("handler");
        switch (type) {
            case SelectionKey.OP_ACCEPT:
                handleAccept(key, selector);
                break;
            case SelectionKey.OP_READ:
                handleRead((SocketChannel) key.channel());
                break;
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) {
        System.out.println("handleAccept");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        System.out.println("serverSocketChannel hashCode:\t" + serverSocketChannel.hashCode());
        try {
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            //将 channel 注册到 Selector
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRead(SocketChannel channel) {
        try {
            //将 channel 注册到 Selector
            List<Film> films = readFilms(channel);
            System.out.println(films);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("readTimes:\t" + ++readTimes);
    }

    private int getKeyType(SelectionKey key) {
        if (key.isAcceptable()) {
            return SelectionKey.OP_ACCEPT;
        }

        if (key.isConnectable()) {
            return SelectionKey.OP_CONNECT;
        }

        if (key.isReadable()) {
            return SelectionKey.OP_READ;
        }

        if (key.isWritable()) {
            return SelectionKey.OP_WRITE;
        }
        return -1;
    }

    private void printKeyStatus(SelectionKey selectionKey) {
        System.out.println("isAcceptable:" + selectionKey.isAcceptable()
                + "\tisConnectable:" + selectionKey.isConnectable()
                + "\tisReadable:" + selectionKey.isReadable()
                + "\tisWritable:" + selectionKey.isWritable()
                + "\tisValid:" + selectionKey.isValid()
        );
    }

    private List<Film> readFilms(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteOutputStream bos = new ByteOutputStream();
        int read = channel.read(buffer);
        while (read > 0) {
            buffer.flip();
            bos.write(buffer.array());
            buffer.clear();
            read = channel.read(buffer);
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bos.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            channel.close();
            return new ArrayList<>();
        }
        @SuppressWarnings("unchecked")
        List<Film> result = (List<Film>) ois.readObject();
        IOUtils.closeQuietly(ois);
        IOUtils.closeQuietly(bos);
        return result;
    }

    private int readTimes = 0;
}
