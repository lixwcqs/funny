package com.cqs.rpc.rmi;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        // RMI注册表
        Registry registry = null;

        try {
            registry = LocateRegistry.createRegistry(8888);

            // 创建一个服务
            HelloService helloService = new HelloServiceImpl();

            // 向RMI注册表注册服务，并将该服务命名为hello-service
            registry.bind("hello-service", helloService);
            System.out.println("> Ok, I could provice RPC service now~");
        } catch (Exception e) {

        }
    }
}