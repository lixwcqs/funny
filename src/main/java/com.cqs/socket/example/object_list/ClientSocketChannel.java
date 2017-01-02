package com.cqs.socket.example.object_list;

import com.cqs.socket.example.demo_nio.Film;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static com.cqs.mock.FilmMock.randFilms;

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
}
