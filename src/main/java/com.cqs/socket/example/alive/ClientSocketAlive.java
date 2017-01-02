package com.cqs.socket.example.alive;

import com.cqs.mock.FilmMock;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 17-1-1.
 */
public class ClientSocketAlive {
    SocketChannel socketChannel = null;

    private final int id = new Random().nextInt(1000);

    public static void main(String[] args) throws IOException {

        ClientSocketAlive clientSocket = new ClientSocketAlive();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                clientSocket.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void run() throws IOException {
        if (socketChannel == null) {
            socketChannel = SocketChannel.open();
            System.out.println("创建新的通道");
        }
        if ( !socketChannel.socket().isConnected() || socketChannel.isConnectionPending()) {
            newSocket();
        }
        send(FilmMock.randFilms(id));
    }

    private int count = 0;

    public void send(Object t) throws IOException {
        System.out.println("send data\t" + ++count);
        ByteArrayOutputStream aos = null;
        ObjectOutputStream oos = null;
        try {
            aos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(aos);
            oos.writeObject(t);
            oos.flush();
            try {
                //server断关闭链接,client不能检测,所以写入会抛出异常,需要在异常抛出的时候
                //关闭原来的链接,并建立新的链接
                socketChannel.write(ByteBuffer.wrap(aos.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
                newSocket();
                socketChannel.write(ByteBuffer.wrap(aos.toByteArray()));
            }
        } finally {
            IOUtils.closeQuietly(aos);
            IOUtils.closeQuietly(oos);
        }
    }

    private void newSocket() throws IOException {
        socketChannel.socket().close();
        socketChannel.close();
        socketChannel = SocketChannel.open();
        socketChannel.socket().connect(new InetSocketAddress("localhost", 9874), 1000 * 60);//1分钟
        socketChannel.socket().setReuseAddress(true);
        socketChannel.configureBlocking(false);
        socketChannel.socket().setKeepAlive(true);
        System.out.println("创建新的链接");
    }
}
