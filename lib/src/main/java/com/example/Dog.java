package com.example;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/17.
 * History:
 */

public class Dog {
    String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Dog setName(String name) {
        this.name = name;
        return this;
    }

    public static void foo(Dog someDog) {
        System.out.println("somedog:" + someDog);
        someDog.setName("dog2");

        someDog = new Dog("dog3");
        System.out.println("somedog:" + someDog);
    }

    public static void main(String[] args) {
        Dog dog = new Dog("dog1");
        System.out.println("dog =:" + dog );
        foo(dog);
        System.out.println("dog =:" + dog );
        System.out.println(dog.getName());
    }
}
