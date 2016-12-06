package com.cqs.socket.example.nio_object_list;

import com.cqs.socket.example.demo_nio.Film;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by cqs on 16-11-21.
 */
public class ServerSocket {

    public static void main(String[] args) {
        ServerSocket socket = new ServerSocket();
        socket.run();
    }

    public void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
//            channel.bind(new InetSocketAddress("localhost",9874));//绑定本地端口，并监听连接
            channel.socket().bind(new InetSocketAddress("localhost", 9874));//绑定本地端口，并监听连接
            channel.socket().setReuseAddress(true);
            channel.configureBlocking(false);
            channel.accept();//接受连接（三次握手之后，建立连接)
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {//轮询[这里不可写成while(true))]
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();//
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    @SuppressWarnings("unchecked")
                    List<Film> films = readFilms(socketChannel);
                    System.out.println(films);
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Film> toFilms(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            List<Film> films = (List<Film>) ois.readObject();
            return films;
        } finally {
            bais.close();
            ois.close();
        }
    }

    private List<Film> readFilms(SocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while (channel.read(buffer) != -1) {
                buffer.flip();
                baos.write(buffer.array());
                buffer.clear();
            }
            byte[] bytes = baos.toByteArray();
            return toFilms(bytes);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(channel);
        }
    }

}
