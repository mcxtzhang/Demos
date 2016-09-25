package com.mcxtzhang.databindingdemo.recyclerview.vm;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/25.
 */

public class FirstBindingBeanViewModel {

    //后面的netUrl是xml里的名字, 必须是static方法
    @BindingAdapter({"bind:netUrl"})
    public static void loadNetImage(ImageView imageView, String url) {
        //图片加载
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
