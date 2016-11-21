package com.cqs.socket.example.object_list;

import com.cqs.socket.example.demo_nio.Film;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cqs on 16-11-21.
 */
public class ClientSocketChannel {

    public static void main(String[] args) {
        ClientSocketChannel socket = new ClientSocketChannel();
        socket.run();
    }

    public void run(){
        try {
            Socket socket = new Socket("localhost",9876);
            List<Film> films = randFilms();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(films);
            oos.flush();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Film> randFilms(){
        Film film = new Film("喜剧之王",9.3f);
        Film film2 = new Film("大话西游",9.0f);
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film2);
        return films;

    }
}
