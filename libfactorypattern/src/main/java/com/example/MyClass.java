package com.example;

import com.example.abstractt.AbstractFoodFactory;
import com.example.abstractt.KFCFoodFactory;
import com.example.abstractt.LzlmFoodFactory;
import com.example.bean.INoodles;
import com.example.bean.LzNoodles;
import com.example.bean.PaoNoodles;
import com.example.normal.GankouFactory;
import com.example.normal.NoodlesFactory;
import com.example.simple.SimpleNoodlesFactory;
import com.example.staticc.StaticNoodlesFactory;

public class MyClass {
    public static void main(String[] args) {
        System.out.println("==============================简单工厂==============================");
        /**
         * 简单工厂模式
         */
        INoodles noodles = SimpleNoodlesFactory.createNoodles(SimpleNoodlesFactory.TYPE_GK);
        noodles.desc();

        /**
         * 利用Class.forName(clz.getName()).newInstance()
         */
        System.out.println("==============================利用Class.forName(clz.getName()).newInstance()==============================" +
                "\n个人觉得不好，因为这样和简单的new一个对象一样，工厂方法应该用于复杂对象的初始化" +
                "\n 这样像为了工厂而工厂");
        //兰州拉面
        INoodles lz = StaticNoodlesFactory.createNoodles(LzNoodles.class);
        lz.desc();
        //泡面
        INoodles pm = StaticNoodlesFactory.createNoodles(PaoNoodles.class);
        pm.desc();

        /**
         * 模仿Executor类
         */
        System.out.println("==============================模仿Executor类==============================" +
                "\n 这种我比较青睐，增加一个新面条，只要去增加一个static方法即可,也不修改原方法逻辑");
        INoodles lz2 = StaticNoodlesFactory.createLz();
        lz2.desc();

        INoodles gk2 = StaticNoodlesFactory.createGk();
        gk2.desc();


        /**
         * 工厂方法：
         */
        System.out.println("==============================工厂方法==============================" +
                "\n 这种要多写一个类，不过更面向对象吧 = = ，实际中我更倾向于使用【模仿Executor类】的方式");
        NoodlesFactory factory1 = new GankouFactory();
        INoodles gk3 = factory1.create();
        gk3.desc();


        /**
         * 抽象工厂方法：
         */
        System.out.println("==============================抽象方法==============================" +
                "\n 老实说，以我这一年的水平我体会不到抽象工厂有何巨大优势，所以在我这里我没有想到很好的使用场景。希望以后在慢慢体会吧。");
        AbstractFoodFactory abstractFoodFactory1 = new KFCFoodFactory();
        abstractFoodFactory1.createDrinks().prices();
        abstractFoodFactory1.createNoodles().desc();

        AbstractFoodFactory abstractFoodFactory2 = new LzlmFoodFactory();
        abstractFoodFactory2.createDrinks().prices();
        abstractFoodFactory2.createNoodles().desc();
    }
}
