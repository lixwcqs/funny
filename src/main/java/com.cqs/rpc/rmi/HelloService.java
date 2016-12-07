package com.cqs.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 服务接口，封装了供远程调用的方法
 */
public interface HelloService extends Remote {
    // 服务方法
    String sayHello(String name) throws RemoteException;
}

class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    // 底层采用JDK提供的序列化机制
    private static final long serialVersionUID = -6399513770403820034L;

    public HelloServiceImpl() throws RemoteException {
        super();
    }

    // 具体的服务方法实现
    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name;
    }

}
