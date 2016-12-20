package com.cqs.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by cqs on 16-12-20.
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    /**
     * Method method参数怎么感觉是多余的
     *
     * @param obj 被代理的对象
     * @param method  被代理的方法[多余的]
     * @param args 被代理的方法方法参数
     * @param proxy 方法代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("cglg模拟方法加强1");
        Object invoke = proxy.invokeSuper(obj, args);
        System.out.println("cglg模拟方法加强2");
        return invoke;
    }

    /**
     * 通过CGlib生成代理对象
     *
     * @return 代理对象
     */
    public Object getTargetProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        // 在调用父类方法时，回调 this.intercept()
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public static void main(String[] args) {
        Object proxyedObject = new Play(2);    // 被代理的对象
        CglibProxy cgProxy = new CglibProxy(proxyedObject);
        IPlay proxyObject = (Play) cgProxy.getTargetProxy();
        proxyObject.playGame("LOL");
        System.out.println("---------------------------");
        proxyObject.playGameFinal("LOL");
    }


}
