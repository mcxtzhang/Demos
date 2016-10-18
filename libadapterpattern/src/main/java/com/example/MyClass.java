package com.example;

import com.example.classadapter.VoltageAdapter;
import com.example.objectadapter.VoltageAdapter2;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("===============类适配器==============");
        Mobile mobile = new Mobile();
        mobile.charging(new VoltageAdapter());
/*        System.out.println("=======如果不用适配器，直接连220V电源========");
        mobile.charging(new Voltage220().output220V());*/

        System.out.println("\n===============对象适配器==============");
        VoltageAdapter2 voltageAdapter2 = new VoltageAdapter2(new Voltage220());
        Mobile mobile2 = new Mobile();
        mobile2.charging(voltageAdapter2);


    }
}
