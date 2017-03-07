package com.mcxtzhang.daggerdemo.chap3;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/7.
 * History:
 */
public interface LoginConrtact {
    interface View {
        void showData();
    }

    interface Presenter{
        void loadData();
    }
}
