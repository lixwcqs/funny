package com.cqs;

import com.cqs.socket.example.demo_nio.Film;
import com.cqs.socket.example.demo_nio.SerializableUtils;

import java.io.*;

/**
 * Created by cqs on 10/29/16.
 */
public class Demo {

    public static void demo1() {
        Film film = new Film("大话西游", 9.0f);
        byte[] byteFilm1 = null;
        try {
            byteFilm1 = SerializableUtils.toBytes(film);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.write(byteFilm1);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteFilm1));
            System.out.println(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000L + 2208988800L);
    }
}
