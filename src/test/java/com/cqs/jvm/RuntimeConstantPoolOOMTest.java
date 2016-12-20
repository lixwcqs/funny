package com.cqs.jvm;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 16-12-10.
 */
public class RuntimeConstantPoolOOMTest {

    RuntimeConstantPoolOOM rcpo = new RuntimeConstantPoolOOM();

    @Test
    public void testF1() throws Exception {
        rcpo.f1();
        TimeUnit.MINUTES.sleep(5);
    }
}