package com.cqs.socket.example.demo_nio;

import java.io.Serializable;

/**
 * Created by cqs on 10/30/16.
 */
public class Film implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private float rate;
    private byte[] bytes;

    public Film(String title, float rate) {
        this.title = title;
        this.rate = rate;
        this.bytes = new byte[1024];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", rate=" + rate +
                ", bytes=" + bytes.length +
                '}';
    }
}
