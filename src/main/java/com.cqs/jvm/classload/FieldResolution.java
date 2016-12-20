package com.cqs.jvm.classload;

/**
 * Created by cqs on 16-12-19.
 */
public class FieldResolution {
    interface Interface0 {
        int a = 1;

        void f();
    }

    static class Parent {
        static int a = 2;

        public void f() {
        }
    }

    static class Sub extends Parent implements Interface0 {

    }

    private int b = 20;

    static {
        c = 15;
        System.out.println(Thread.currentThread() + " vinit");
//        if (true)
//        while(true);
//        System.out.println(c);
//        c += 1;
    }

    private static int c = 20;

    public FieldResolution() {
        System.out.println(Thread.currentThread().getName() + "\t" + b + "\t" + c);
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println(Thread.currentThread() + "执行开始");
            new FieldResolution();
            System.out.println(Thread.currentThread() + "执行结束");
        };
        for (int i = 0; i < 2; i++) {
            new Thread(r).start();
        }
    }

}
