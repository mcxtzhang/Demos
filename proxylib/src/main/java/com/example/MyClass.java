package com.example;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

public class MyClass {
    public static void main(String[] args) {
        final RealSubJect realSubJect = new RealSubJect();
        ISubject proxySubject = (ISubject) Proxy.newProxyInstance(ISubject.class.getClassLoader(),
                new Class[]{ISubject.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        System.out.println("invoke method...");
                        return method.invoke(realSubJect, objects);
                    }
                });

        proxySubject.doSth();

        //write proxySubject class binary data to file
        createProxyClassFile();


    }

    public static void createProxyClassFile() {
        String name = "ProxyISubject";
        byte[] data = ProxyGenerator.generateProxyClass(name, new Class[]{ISubject.class});
        try {
            FileOutputStream out = new FileOutputStream(name + ".class");
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
