package com.mcxtzhang.daggerdemo;

import android.util.Log;

import javax.inject.Inject;

public class SimpleMaker implements CoffeeMaker {
    Cooker mCooker;

    @Inject
    public SimpleMaker(Cooker cooker) {
        mCooker = cooker;
    }

    @Override
    public String makeCoffee() {
        return mCooker.make();
    }

    @Inject
    public void injectFinished() {
        Log.d("Dagger", "injectFinishedddddddd() called");
    }
}