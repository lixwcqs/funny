package com.cqs.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * Created by cqs on 16-12-19.
 */
public class JdkProxyDemo {


    /***
     * JDK 动态代理三要素:
     * classLoader:加载接口,实现Java对象的动态生成
     * 接口:申明接口
     * InvocationHandler:(代理模式的实现类[和静态代理模式太像了])
     */
    public void proxy() {
        IPlay play = new Play(randInt());
        //这里必须为接口,因Proxy生成类型为com.cqs.proxy.$Proxy0,其不能装换成Play类型
        IPlay playProxy = (IPlay) Proxy.newProxyInstance(Play.class.getClassLoader(),
                play.getClass().getInterfaces(), (proxy, method, args) -> {
                    System.out.println("模拟方法加强[1]");
                    Object obj = method.invoke(play, args);
                    System.out.println("模拟方法加强[2]");
                    return  obj;
                });
        System.out.println(playProxy.getClass());
        playProxy.playGame("英雄联盟");
        System.out.println("---------------------------");
        playProxy.playGameFinal("英雄联盟");
    }

    public static void main(String[] args) throws Throwable {
        new JdkProxyDemo().proxy();
        //new JdkProxyDemo().invocationHandlerDemo();
    }





    private class PlayInvocationHandler implements InvocationHandler {

//        private T object;
//
//        public PlayInvocationHandler(T object) {
//            this.object = object;
//        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(proxy, args);
        }
    }

    static int randInt() {
        return new Random().nextInt() * 100;
    }

    //InvocationHandler的invoke
    private void invocationHandlerDemo() throws Throwable {
        Play play = new Play(new Random().nextInt());
        PlayInvocationHandler playInvocationHandler = new PlayInvocationHandler();
        for (Method method : play.getClass().getDeclaredMethods()) {
            System.out.println(method);
        }
        Method method = play.getClass().getDeclaredMethod("playGame", String.class);
        String[] args = {"王者荣耀"};
        playInvocationHandler.invoke(play, method, args);
        Method m = JdkProxyDemo.class.getDeclaredMethod("randInt");
        //静态的方法的调用
        System.out.println("静态方法randInt返回:\t" + playInvocationHandler.invoke(null, m, null));

    }

}
