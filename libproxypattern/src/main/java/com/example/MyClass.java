package com.example;

import java.lang.reflect.Proxy;

public class MyClass {

    public static void main(String[] args) {
        TimeRecorderHandler timeRecorderHandler = new TimeRecorderHandler(new TargetImpl());
        ITarget TargetImplProxy = (ITarget) Proxy.newProxyInstance(ITarget.class.getClassLoader(), new Class[]{ITarget.class}, timeRecorderHandler);
        System.out.println("TargetImplProxy:"+TargetImplProxy);


        TargetImplProxy.thing1();
        TargetImplProxy.thing2();
        TargetImplProxy.thing3();
    }
}
