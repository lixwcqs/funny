package com.cqs.socket.example.nio_object_list;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static com.cqs.mock.FilmMock.randFilms;

/**
 * Created by cqs on 16-11-21.
 */
public class ClientSocket {

    public static void main(String[] args) {
        ClientSocket socket = new ClientSocket();
        socket.run();
    }

    public void run() {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.socket().connect(new InetSocketAddress("localhost", 9874));
            channel.socket().setReuseAddress(true);
            channel.configureBlocking(false);//非阻塞
            sendFilms(channel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(channel);
        }
    }

    public void sendFilms(SocketChannel channel) {
        //ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(randFilms());
            oos.flush();
            channel.write(ByteBuffer.wrap(bos.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(bos);
        }

    }

}
