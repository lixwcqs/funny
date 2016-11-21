package com.cqs.socket.example.object_list;

import com.cqs.socket.example.demo_nio.Film;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * Created by cqs on 16-11-21.
 */
public class ServerSocketChannel {

    public static void main(String[] args) {
        ServerSocketChannel socket = new ServerSocketChannel();
        socket.run();
    }

    public void run(){
        try {
            java.net.ServerSocket severSocket = new java.net.ServerSocket();
            severSocket.bind(new InetSocketAddress("localhost",9876));
            Socket socket = severSocket.accept();//监听并建立连接
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            List<Film> object = (List<Film>) ois.readObject();
            System.out.println(object);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
