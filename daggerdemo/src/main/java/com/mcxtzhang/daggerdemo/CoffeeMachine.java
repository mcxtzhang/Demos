package com.mcxtzhang.daggerdemo;

import android.util.Log;

import javax.inject.Inject;

/**
 * 这是一个制作Coffee的例子
 * CoffeeMaker是对制作Coffee过程的一个封装
 * 制作Coffee需要实现CoffeeMarker的makeCoffee方法
 */
public class CoffeeMachine {
    private CoffeeMaker mCoffeeMaker;

    @Inject
    public CoffeeMachine(SimpleMaker maker) {
        mCoffeeMaker = maker;
    }

    public String makeCoffee() {
        return mCoffeeMaker.makeCoffee();
    }

    @Inject
    public void initSuccess() {
        Log.d("Dagger", "initSuccess() called");
    }
}