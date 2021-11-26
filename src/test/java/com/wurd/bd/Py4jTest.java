package com.wurd.bd;

import py4j.GatewayServer;

import java.util.ArrayList;
import java.util.List;

/**
 * python 3.9
 * 安装：python install py4j==0.10.9.1
 *
 * from py4j.java_gateway import JavaGateway
 * gateway = JavaGateway()
 * obj = gateway.entry_point.get()
 * obj.add("123")
 *
 */
public class Py4jTest {
    public static void main(String[] args) {
        //默认端口： 25333
        GatewayServer gatewayServer = new GatewayServer(new EntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }
}

class EntryPoint {
    private List<Object> list;

    public EntryPoint() {
        list = new ArrayList<>();
    }

    public List<Object> get() {
        return list;
    }
}
