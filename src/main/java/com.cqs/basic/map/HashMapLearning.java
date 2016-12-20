package com.cqs.basic.map;

import java.util.HashMap;

/**
 * Created by cqs on 16-12-12.
 */
public class HashMapLearning {
    private  HashMap map = new HashMap();

    public static void main(String[] args) {
        HashMapLearning hml = new HashMapLearning();
        hml.keyNullable();
    }

    /**
     * HashMap键/值均可以为null
     */
    public  void keyNullable() {
        map.put(null, null);
        System.out.println(map.get(null));
        System.out.println(map.size());
    }
}


