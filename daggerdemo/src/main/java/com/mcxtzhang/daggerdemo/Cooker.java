package com.mcxtzhang.daggerdemo;

import android.util.Log;

import javax.inject.Inject;

public class Cooker {
    private static final String TAG = "Dagger";
    String name; //咖啡师名字
    String coffeeKind; //制作咖啡的类型

    public Cooker(String name, String coffeeKind) {
        this.name = name;
        this.coffeeKind = coffeeKind;
    }


    public String make() {
        return name + " make " + coffeeKind; //咖啡师制作Coffee的过程
    }

    @Inject
    public void injectFinished() {
        Log.d(TAG, "injectFinished() called");
    }
}