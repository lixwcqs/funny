package com.cqs.rpc.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        // RMI注册表
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 8888);
            // 根据服务名称获取服务
            HelloService helloService = (HelloService) registry.lookup("hello-service");
            // 调用远程方法
            System.out.println("RMI 服务器返回的结果是：" + helloService.sayHello("flyne"));
        } catch (Exception e) {

        }
    }
}