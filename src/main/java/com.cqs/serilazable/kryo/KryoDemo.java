package com.cqs.serilazable.kryo;


import com.cqs.mock.StrategyMock;
import com.cqs.serilazable.Strategy;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Stopwatch;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 16-12-4.
 */
public class KryoDemo {

    public void kryoSerializeToFile() throws FileNotFoundException {
        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream("tmp/strategy.bin"));
        Strategy strategy = StrategyMock.randStrategy();
        kryo.writeObject(output, strategy);
        output.close();
        // ...
        Input input = new Input(new FileInputStream("tmp/strategy.bin"));
        Strategy strategy1 = kryo.readObject(input, Strategy.class);
        System.out.println(strategy1);
        input.close();
    }

    public void kryoCopy() {
        Kryo kryo = new Kryo();
        Strategy strategy = StrategyMock.randStrategy();
        Strategy copy = kryo.copy(strategy);//深拷贝
        Strategy copy2 = kryo.copyShallow(strategy);//浅拷贝
        System.out.println(strategy.equals(copy));
        System.out.println(strategy + "**************\n" + copy);
        System.out.println(copy2);
    }

    public void kryoWriteAndReadObject(){
        Stopwatch stopwatch = Stopwatch.createStarted();
        //stopwatch.start();
        Kryo kryo = new Kryo();
        kryo.register(List.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        List<Strategy> list = new ArrayList<>();
        for (int i =0; i < 10000; ++i){
            list.add(StrategyMock.randStrategy());
        }
        kryo.writeObject(output,list);
        try {
            baos.flush();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Input input = new Input(bais);
        List list2 = kryo.readObject(input,ArrayList.class);
        System.out.println(list2.size());
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        try {
            input.close();
            IOUtils.closeQuietly(bais);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
