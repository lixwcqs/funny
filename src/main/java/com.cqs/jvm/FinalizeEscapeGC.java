package com.cqs.jvm;

import java.util.concurrent.TimeUnit;

/**
 * -XX:+PrintGCDetails    -XX:+PrintGCDateStamps
 * Created by cqs on 16-12-11.
 */
public class FinalizeEscapeGC {
    private static FinalizeEscapeGC SAVE_HOOK = null;

    private void isAlive() {
        System.out.println("还活着");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize 方法尚未执行");
        FinalizeEscapeGC.SAVE_HOOK = this;//自救
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();
        saveHook();
        saveHook();
    }

    private static void saveHook() throws InterruptedException {
        SAVE_HOOK = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("SAVE_HOOK对象挂了");
        }
    }
}
