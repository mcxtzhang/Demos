package com.mcxtzhang.daggerdemo.chap3;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/7.
 * History:
 */
public class LoginPresenter implements LoginConrtact.Presenter {
    private LoginConrtact.View mView;
    private String mTag;

    public LoginPresenter(LoginConrtact.View view, String tag) {
        mView = view;
        mTag = tag;
    }

    @Override
    public void loadData() {
        mView.showData();
    }
}
