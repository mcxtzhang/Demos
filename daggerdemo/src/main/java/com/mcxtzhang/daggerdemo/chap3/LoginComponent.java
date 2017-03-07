package com.mcxtzhang.daggerdemo.chap3;

import dagger.Component;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/7.
 * History:
 */
@Component(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
