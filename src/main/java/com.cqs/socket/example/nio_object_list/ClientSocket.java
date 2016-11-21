package com.cqs.socket.example.nio_object_list;

import com.cqs.socket.example.demo_nio.Film;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

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

    List<Film> randFilms() {
        Film film = new Film("喜剧之王", 9.3f);
        Film film2 = new Film("大话西游", 9.0f);
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film2);
        return films;

    }
}
