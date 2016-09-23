package com.mcxtzhang.databindingdemo;

import android.view.View;
import android.widget.Toast;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/23.
 */

public class MainPresenter {
    private MainActivity mainActivity;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBtnClick(View v) {
        Toast.makeText(mainActivity, "点击", Toast.LENGTH_SHORT).show();
    }
}
