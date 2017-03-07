package com.mcxtzhang.daggerdemo.chap3;

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

    @Provides
    public LoginConrtact.Presenter provideLoginPresenter(LoginConrtact.View view) {
        return new LoginPresenter(view, mTag);
    }

    @Provides
    public LoginConrtact.View provideView(){
        return mView;
    }
}
