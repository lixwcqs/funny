package com.cqs.socket.example;

import java.io.Serializable;

/**
 * Created by cqs on 10/29/16.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String password;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
