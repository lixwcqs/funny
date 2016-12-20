package com.cqs.proxy;

import java.util.Random;

public class Play implements IPlay {
    private int id;

    public Play() {
        id = new Random().nextInt();
    }

    public Play(int id) {
        this.id = id;
    }

    public void playGame(String game) {
        System.out.println("玩家(id = " + id + ")在玩" + game);
    }

    @Override
    public final void playGameFinal(String game) {
        System.out.println(this.getClass() + "玩家(id = " + id + ")在玩" + game + "[final方法]");
    }
}

interface IPlay {
    void playGame(String game);

    void playGameFinal(String game);
}