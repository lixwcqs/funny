package com.cqs.jvm;

/**
 * Created by cqs on 16-12-10.
 */
public class StringDemo {

    public void reference(){
        /**
         * 通过String申明的变量返回的均是堆地址
         * 常量池只是String值的一个'缓存'
         * 若常量池中存在该值,那么堆中就不新建String对象,而是将堆地址指向常量池
         */
        String str1 = new StringBuilder("计算机").append("软件").toString();
        //str1和str1.intern()返回的是同一个堆地址[JDK1.7版本以上]
        System.out.println(str1 == str1.intern());

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2 == str2.intern());
        //返回的都是常量池"计算机"."软件"的地址而不是堆地址
        System.out.println(str1.intern() == new StringBuilder("计算机软件").toString().intern());
        //虽然两者的值是相等的[引用指向相同,但是引用地址本身不同,即两者是堆上两个指向相同的不同引用],
        System.out.println(new StringBuilder("计算机软件").toString() == new StringBuilder("计算机软件").toString());
    }
}
