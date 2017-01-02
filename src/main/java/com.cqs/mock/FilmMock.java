package com.cqs.mock;

import com.cqs.socket.example.demo_nio.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cqs on 17-1-1.
 */
public class FilmMock {

    public static List<Film> randFilms() {
        Film film = new Film("喜剧之王", 9.3f);
        Film film2 = new Film("大话西游", 9.0f);
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film2);
        return films;

    }

    public static List<Film> randFilms(int id) {
        Film film = new Film("喜剧之王" + id, 9.3f);
        Film film2 = new Film("大话西游", 9.0f);
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film2);
        return films;

    }
}
