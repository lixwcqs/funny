package com.cqs.nio;

import com.google.common.base.Stopwatch;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 10/30/16.
 */
public class FileChannelDemo {

    public static void sendFile(OutputStream os, RandomAccessFile raFile) throws IOException {
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

    public static void copyFile() throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();//new
        RandomAccessFile source = new RandomAccessFile("/home/cqs/Documents/《武林外传》未删节台湾清晰版/[武林外传].01.郭女侠怒砸同福店.佟掌柜妙点迷路人.avi", "r");
        OutputStream os = new FileOutputStream(new File("01.郭女侠怒砸同福店.佟掌柜妙点迷路人.avi"));
        sendFile(os, source);
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static void main(String[] args) {

    }

}
