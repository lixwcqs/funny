package com.cqs.serilazable.kryo;

import org.junit.Test;

/**
 * Created by cqs on 16-12-6.
 */
public class KryoDemoTest {

    KryoDemo kyro = new KryoDemo();

    @Test
    public void kryoSerializeToFile() throws Exception {
        kyro.kryoSerializeToFile();
    }

    @Test
    public void kryoCopy() throws Exception {
        kyro.kryoCopy();
    }

    @Test
    public void kryoWriteAndReadObject() throws Exception {
        kyro.kryoWriteAndReadObject();
    }

}