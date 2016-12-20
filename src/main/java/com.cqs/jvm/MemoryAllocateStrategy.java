package com.cqs.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx20m -Xms20m -Xmn10m
 * Created by cqs on 16-12-11.
 */
public class MemoryAllocateStrategy {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
//        allocation4();
//        testPretenureSizeThreshold();
        testTurningThreshold();
    }

    private static void allocation1() {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        System.gc();
        byte[] allocation4 = new byte[6 * _1MB];
    }

    private static void allocation2() {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        byte[] allocation4 = new byte[6 * _1MB];
    }

    private static void allocation3() {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        byte[] allocation4 = new byte[2 * _1MB];
    }

    private static void allocation4() {
        byte[] allocation1 = new byte[2 * _1MB];
        byte[] allocation2 = new byte[2 * _1MB];
        byte[] allocation3 = new byte[2 * _1MB];
        byte[] allocation4 = new byte[4 * _1MB];
    }

    private static void testPretenureSizeThreshold() {
        byte[] allocation = new byte[2 * _1MB];
        //eden不足以分配,新分配的大对象直接通过担保机制进入老年代
        byte[] pretenure = new byte[5 * _1MB];
    }

    /**
     * 分别将MaxTenutringThreshold设置为1和15
     * -XX:MaxTenutringThreshold=1
     *
     * ???MaxTenutringThreshold根本不起作用啊
     */

    /**
     * ???MaxTenutringThreshold根本不起作用啊
     *
     * -XX:MaxTenutringThreshold=1
     * 016-12-12T07:43:11.808+0800: [GC (Allocation Failure) [PSYoungGen: 8015K->992K(9216K)] 8015K->7144K(19456K), 0.0039181 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     2016-12-12T07:43:11.812+0800: [Full GC (Ergonomics) [PSYoungGen: 992K->0K(9216K)] [ParOldGen: 6152K->7069K(10240K)] 7144K->7069K(19456K), [Metaspace: 3085K->3085K(1056768K)], 0.0085111 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     Heap
     PSYoungGen      total 9216K, used 2883K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     eden space 8192K, 35% used [0x00000000ff600000,0x00000000ff8d0c60,0x00000000ffe00000)
     from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     ParOldGen       total 10240K, used 7069K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     object space 10240K, 69% used [0x00000000fec00000,0x00000000ff2e7438,0x00000000ff600000)
     Metaspace       used 3176K, capacity 4494K, committed 4864K, reserved 1056768K
     class space    used 349K, capacity 386K, committed 512K, reserved 1048576K


     -XX:MaxTenutringThreshold=15
     2016-12-12T07:44:26.047+0800: [GC (Allocation Failure) [PSYoungGen: 8015K->992K(9216K)] 8015K->7168K(19456K), 0.0048166 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     2016-12-12T07:44:26.051+0800: [Full GC (Ergonomics) [PSYoungGen: 992K->0K(9216K)] [ParOldGen: 6176K->7066K(10240K)] 7168K->7066K(19456K), [Metaspace: 3045K->3045K(1056768K)], 0.0059943 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     Heap
     PSYoungGen      total 9216K, used 2883K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     eden space 8192K, 35% used [0x00000000ff600000,0x00000000ff8d0c48,0x00000000ffe00000)
     from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     ParOldGen       total 10240K, used 7066K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     object space 10240K, 69% used [0x00000000fec00000,0x00000000ff2e6b50,0x00000000ff600000)
     Metaspace       used 3092K, capacity 4494K, committed 4864K, reserved 1056768K
     class space    used 338K, capacity 386K, committed 512K, reserved 1048576K

     */
    private static void testTurningThreshold() {
        List list = new ArrayList();
        // i < 12 , i < 18
        for (int i = 0; i < 18; ++i){
            byte[] allocation1 = new byte[_1MB / 2];
            list.add(allocation1);
        }
        //gc触发时不能回收allocation1,age+1,判定等于MaxTenutringThreshold阈值,进入老年代
    }


    /**
     * 分别将MaxTenutringThreshold设置为1和15
     * -XX:MaxTenutringThreshold=1
     */
    private static void testTurningThreshold2() {
        byte[] allocation1 = new byte[_1MB / 4];
        byte[] allocation2 = new byte[4 * _1MB];
        byte[] allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        System.gc();
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 分别将MaxTenutringThreshold设置为1和15
     * -XX:MaxTenutringThreshold=1
     */
    private static void testTurningThreshold3() {
        byte[] allocation1 = new byte[_1MB / 4];
        byte[] allocation2 = new byte[4 * _1MB];
        byte[] allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }
}
