package com.cqs;

import java.util.List;

/**
 * Created by cqs on 16-12-25.
 */
public class Demo2 {
    public String strParam(String original){
        int xxxx = 100;
        return original += "2";
    }

    public void listParam(List<String> list){
        list.add("Hello");
    }
}
