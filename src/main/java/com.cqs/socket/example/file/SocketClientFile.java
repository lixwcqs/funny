package com.cqs.socket.example.file;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
public class SocketClientFile {

    /**
     * 向ServerSocket中发送文件
     */
    public void sendFile() {
        String path = "/home/cqs/Downloads/gradle-3.1-bin.zip";
        path = "/home/cqs/Downloads/algs4-master.zip";
        RandomAccessFile source = null;
        ByteArrayOutputStream baos = null;
        SocketChannel channel = null;
        try {
            source = new RandomAccessFile(path, "r");
            baos = new ByteArrayOutputStream();
            writeFile(baos, source);
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

    /**
     * 将文件写入到输出流中
     * @param os
     * @param raFile
     * @throws IOException
     */
    private static void writeFile(OutputStream os, RandomAccessFile raFile) throws IOException {
        FileChannel channel = raFile.getChannel();
        final int SIZE = 1024 * 1024;//1M
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        byte[] bytes = new byte[SIZE];
        int length;
        while ((length = channel.read(buffer)) != -1) {
            buffer.flip();
            buffer.get(bytes, 0, length);
            os.write(bytes);
            buffer.clear();
        }
        os.flush();
        IOUtils.closeQuietly(channel);
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
