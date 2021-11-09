package com.wurd.bd.internalTest;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class test4 {
    public static void main(String[] args) throws Throwable {
        new B().dodo();
        System.out.println(StringDeserializer.class.getName());
    }
}

class B extends A {
    public void dodo() throws Throwable {
        Class<?> cls = Proxy.getProxyClass(this.getClass().getClassLoader(), I.class);
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                if(method.getName().equals("dodo")){
                    result = "invoke dodo";
                }
                return result;
            }
        };
        Object instance = cls.getConstructor(InvocationHandler.class).newInstance(handler);
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            Object res = handler.invoke(instance, method, null);
            System.out.println(res);
        }
        System.out.println(B.class.getName() + ": " + "dodo");
    }
}

class A implements I {
    private String a = "a";

    public void dodo() throws Throwable {
        System.out.println(A.class.getName() + ": " + "dodo");
        System.out.println(a);
    }
}

interface I {
    public void dodo() throws Throwable;
}