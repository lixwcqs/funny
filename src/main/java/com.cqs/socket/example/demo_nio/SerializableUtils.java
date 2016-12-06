package com.cqs.socket.example.demo_nio;


import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by cqs on 10/30/16.
 */
public class SerializableUtils {

    public static byte[] toBytes(Film film) throws IOException {
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

    public static Film toFilm(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            Film film = (Film) ois.readObject();
            return film;
        } finally {
            bais.close();
            ois.close();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        byte[] bytes = toBytes(new Film("大话西游", 9.3f));
        Film film = toFilm(bytes);
        System.out.println(film);
    }
}
