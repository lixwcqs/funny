package com.cqs.socket.example.demo3;

import com.cqs.socket.example.demo2.SerializableUtil;
import com.cqs.socket.example.demo_nio.Film;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyClient3 {

    private final static Logger logger = Logger.getLogger(MyClient3.class.getName());

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            new Thread(new MyRunnable(idx)).start();
        }
    }

    private static final class MyRunnable implements Runnable {

        private final int idx;

        private MyRunnable(int idx) {
            this.idx = idx;
        }

        public void run() {
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open();
                SocketAddress socketAddress = new InetSocketAddress("localhost", 10006);
                socketChannel.connect(socketAddress);

                Film film = new Film("film" + idx, 8.3f);
                sendData(socketChannel, film);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    socketChannel.close();
                } catch (Exception ex) {
                }
            }
        }

        private void sendData(SocketChannel socketChannel, Film film) throws IOException {
            byte[] bytes = SerializableUtil.toBytes(film);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            socketChannel.write(buffer);
            socketChannel.socket().shutdownOutput();
        }

        private void sendFilm(SocketChannel socketChannel, Film film) throws IOException {
            byte[] bytes = SerializableUtil.toBytes(film);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            socketChannel.write(buffer);
            socketChannel.socket().shutdownOutput();
        }

        private Film receiveData(SocketChannel socketChannel) throws IOException {
            Film film = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                byte[] bytes;
                int count = 0;
                while ((count = socketChannel.read(buffer)) >= 0) {
                    buffer.flip();
                    bytes = new byte[count];
                    buffer.get(bytes);
                    baos.write(bytes);
                    buffer.clear();
                }
                bytes = baos.toByteArray();
                Object obj = SerializableUtil.toObject(bytes);
                film = (Film) obj;
                socketChannel.socket().shutdownInput();
            } finally {
                try {
                    baos.close();
                } catch (Exception ex) {
                }
            }
            return film;
        }
    }
}
