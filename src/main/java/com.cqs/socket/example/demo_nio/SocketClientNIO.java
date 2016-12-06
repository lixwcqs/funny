package com.cqs.socket.example.demo_nio;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cqs on 10/30/16.
 */
public class SocketClientNIO {


    public static void main(String[] args) {

        SocketClientNIO client = new SocketClientNIO();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            service.submit(client::run);
        }
        service.shutdown();
    }



    public void run() {
        SocketChannel channel = null;
        try {
            //
            channel = SocketChannel.open();
//            channel.socket().setKeepAlive(true);
            channel.connect(new InetSocketAddress("localhost", 10005));

            sendFilm(channel, new Film("大话西游", 9.0f));
            sendFilm(channel, new Film("湄公河行动", 8.2f));

            IOUtils.closeQuietly(channel.socket());
            IOUtils.closeQuietly(channel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(channel);
        }
    }

    private void sendFilm(SocketChannel channel, Film film) {
        try {
            channel.write(ByteBuffer.wrap(toBytes(film)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendFile(SocketChannel channel, RandomAccessFile file) {
        FileChannel fileChannel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            while (fileChannel.read(buffer) != -1){
                buffer.flip();
                while (buffer.hasRemaining()){
                    channel.write(buffer);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] toBytes(Film film) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(film);
            return baos.toByteArray();
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(baos);
        }
    }
}
