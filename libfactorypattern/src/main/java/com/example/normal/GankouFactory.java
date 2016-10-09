package com.example.normal;

import com.example.bean.GankouNoodles;
import com.example.bean.INoodles;

/**
 * 介绍：干扣面工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class GankouFactory extends NoodlesFactory {
    @Override
    public INoodles create() {
        return new GankouNoodles();
    }
}
