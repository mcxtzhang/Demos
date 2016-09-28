package com.mcxtzhang.databindingdemo.recyclerview.multype;

import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.zxtcommonlib.databinding.base.mul.IBaseMulInterface;

/**
 * 介绍：多种Item的Bean2
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/27.
 */

public class MBean2 implements IBaseMulInterface {
    private String image;
    private String txt;

    public MBean2(String image, String txt) {
        this.image = image;
        this.txt = txt;
    }

    public String getImage() {
        return image;
    }

    public MBean2 setImage(String image) {
        this.image = image;
        return this;
    }

    public String getTxt() {
        return txt;
    }

    public MBean2 setTxt(String txt) {
        this.txt = txt;
        return this;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_mul_2;
    }
}
