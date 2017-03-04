package com.mcxtzhang.daggerdemo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mcxtzhang on 2017/3/4.
 */

@Module
public class SimpleMakerModule {
    @Provides
    Cooker provideCooker() {
        return new Cooker("zzz","bb");
    }
}
