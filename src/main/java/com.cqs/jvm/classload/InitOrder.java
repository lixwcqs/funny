package com.cqs.jvm.classload;

/**
 * Created by cqs on 16-12-19.
 */
public class InitOrder {

    private final static String b = "Hello";

    static {
        System.out.println(b);
//        b = "world";
    }

    public InitOrder() {
        a = 100;
//        b = "Hello world";
    }
    private int a = 1;

    public static void main(String[] args) {
//        InitOrder initOrder = new InitOrder();
//        System.out.println(initOrder.a);
//        System.out.println(InitOrder.b);
    }
}

class C2{
    static {
        System.out.println("c2");
    }
}
