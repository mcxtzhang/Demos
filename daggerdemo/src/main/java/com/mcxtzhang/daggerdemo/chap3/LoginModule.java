package com.mcxtzhang.daggerdemo.chap3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/7.
 * History:
 */
@Module
public class LoginModule {

    String mTag;
    LoginConrtact.View mView;

    public LoginModule(String tag, LoginConrtact.View view) {
        mTag = tag;
        mView = view;
    }

    // the params is provided by other @Provides
    @Singleton
    @Provides
    public LoginConrtact.Presenter provideLoginPresenter(/*@Named("filled")*/@WhichView("filled") LoginConrtact.View view) {
        return new LoginPresenter(view, mTag);
    }

    /**
     * 只有相同的@Named的@Inject成员变量与@Provides方法才可以被对应起来。
     * 更常用的方法是使用注解@Qualifier来自定义注解。
     */
    @Singleton
    @WhichView
    //@Named("filled")
    @Provides
    public LoginConrtact.View provideView() {
        return mView;
    }

    @Singleton
    @WhichView("empty")
    //test for @Named
    //@Named("empty")
    @Provides
    public LoginConrtact.View provideEmptyView() {
        return null;
    }
}
