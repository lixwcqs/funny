package com.cqs.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 16-12-10.
 */
public class DirectMemoryOOM {
    private static final  int _1M = 1024 * 1024;
    public static void main(String[] args) throws IllegalAccessException, InterruptedException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1M);
            TimeUnit.MILLISECONDS.sleep(10);
            //System.out.println("--");
        }
    }
}
