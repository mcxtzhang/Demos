package com.mcxtzhang.databindingdemo;

import android.databinding.ObservableArrayMap;
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

    //循默认的方法签名
    public void onBtnClick(View v) {
        Toast.makeText(mainActivity, "点击", Toast.LENGTH_SHORT).show();
    }

    //循默认的方法签名
    public void onEditChange(CharSequence s, int start, int before, int count) {
        Toast.makeText(mainActivity, s.toString(), Toast.LENGTH_SHORT).show();
    }

    //不遵循默认的方法签名：
    public void onCstEditChangeListener(TestBean testBean) {
        Toast.makeText(mainActivity, testBean.getName(), Toast.LENGTH_SHORT).show();
    }

    //改变同名属性的 两个bean 看看是否刷新
    public void changeTestBean(TestBean testBean,TestBean2 testBean2){
        Toast.makeText(mainActivity, "我改变了 ", Toast.LENGTH_SHORT).show();
        testBean.setName("我改变了 ");
        testBean2.setName("同名name也变了");
    }

    private ObservableArrayMap<String,Object> testCollectionMap;

    public void setTestCollectionMap(ObservableArrayMap testCollectionMap) {
        this.testCollectionMap = testCollectionMap;
    }

    //测试改变集合 是否立刻刷新
    public void onMapClick(View view){
        Toast.makeText(mainActivity, "集合也能立刻改变 ", Toast.LENGTH_SHORT).show();
        testCollectionMap.put("lastName","集合也能立刻改变");
    }


}
