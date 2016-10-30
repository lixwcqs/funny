package com.cqs.socket.example.file;

import com.cqs.nio.FileChannelDemo;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cqs on 10/30/16.
 */
public class SocketClientFile {

    public void sendFile() {
        String path = "/home/cqs/Downloads/gradle-3.1-bin.zip";
        path = "/home/cqs/Downloads/algs4-master.zip";
        RandomAccessFile source = null;
        ByteArrayOutputStream baos = null;
        SocketChannel channel = null;
        try {
            source = new RandomAccessFile(path, "r");
            baos = new ByteArrayOutputStream();
            FileChannelDemo.sendFile(baos, source);
            channel = SocketChannel.open(new InetSocketAddress("localhost", 10007));
            channel.write(ByteBuffer.wrap(baos.toByteArray()));
            System.out.println("传输完毕");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(channel);
            IOUtils.closeQuietly(baos);
        }
    }

    public static void main(String[] args) {
        SocketClientFile client = new SocketClientFile();
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.submit(client::sendFile);
        }
        exec.shutdown();
    }

}
