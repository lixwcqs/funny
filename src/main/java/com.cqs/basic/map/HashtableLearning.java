package com.cqs.basic.map;

import java.util.Hashtable;

public class HashtableLearning {
    private static Hashtable map = new Hashtable();

    public static void main(String[] args) {
        HashtableLearning htl = new HashtableLearning();
        htl.keyNotNullable();
    }

    /**
     * Hashtable键,值均不能为空
     */
    public void keyNotNullable() {
        try {
            map.put(null, 1);
        } catch (Exception e) {
            System.out.println("hashtable键不能为空");
        }

        try {
            map.put("1", null);
        } catch (Exception e) {
            System.out.println("hashtable值不能为空");
        }
        System.out.println(map.size());
    }
}