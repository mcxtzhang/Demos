package com.mcxtzhang.daggerdemo;

import dagger.Component;

/**
 * Created by mcxtzhang on 2017/3/4.
 */

@Component(modules = SimpleMakerModule.class)
public interface SimpleComponent {
    void inject(MainActivity mainActivity);
}
