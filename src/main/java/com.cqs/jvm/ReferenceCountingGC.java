package com.cqs.jvm;

/**
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -Xloggc:/home/cloud/Desktop/gc.log
 * -Xmx8m
 * -Xms8m
 * Created by cloud on 16-12-10.
 *
 */
public class ReferenceCountingGC {
    private Object intance = null;

    private static final int _1M = 1024 * 1024;
    private byte[] bigSize = new byte[_1M];


    private void gc1(){
        ReferenceCountingGC gc = new ReferenceCountingGC();
        ReferenceCountingGC gc1 = new ReferenceCountingGC();
        gc = null;
        gc1 = null;
        System.gc();
    }

    /***
     * 单独运行f1(),f2()回收日志相同,说明JVM没有使用计数引用算法
     */
    private void gc2(){
        ReferenceCountingGC gc = new ReferenceCountingGC();
        ReferenceCountingGC gc1 = new ReferenceCountingGC();
        gc1.intance = gc;
        gc.intance = gc1;
        gc = null;
        gc1 = null;
        System.gc();
    }

    public static void main(String[] args) {
        ReferenceCountingGC gc = new ReferenceCountingGC();
        ReferenceCountingGC gc1 = new ReferenceCountingGC();
        gc1.intance = gc;
        gc.intance = gc1;
        gc = null;
        gc1 = null;
        System.gc();
    }
}
