package com.cqs.jvm;

/**
 * Created by cqs on 16-12-8.
 */
public class RuntimeConstantPoolOOM {
    private static final String str = "Hello World";

    StringDemo stringDemo = new StringDemo();

    public void f1() {
        stringDemo.reference();
    }
}
