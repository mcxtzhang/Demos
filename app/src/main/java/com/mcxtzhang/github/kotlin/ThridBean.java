package com.mcxtzhang.github.kotlin;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/18.
 * History:
 */

public class ThridBean {
    @Override
    public String toString() {
        return "ThridBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", price=" + price +
                '}';
    }

    String name;
    int age;
    double price;

    public String getName() {
        return name;
    }

    public ThridBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public ThridBean setAge(int age) {
        this.age = age;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ThridBean setPrice(double price) {
        this.price = price;
        return this;
    }
}
