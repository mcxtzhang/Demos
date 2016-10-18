package com.example.normal;

import com.example.bean.INoodles;
import com.example.bean.LzNoodles;

/**
 * 介绍：兰州拉面工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class LzFactory extends NoodlesFactory {
    @Override
    public INoodles create() {
        return new LzNoodles();
    }
}
