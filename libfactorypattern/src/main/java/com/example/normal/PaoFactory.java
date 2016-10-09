package com.example.normal;

import com.example.bean.INoodles;
import com.example.bean.PaoNoodles;

/**
 * 介绍：泡面工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class PaoFactory extends NoodlesFactory {
    @Override
    public INoodles create() {
        return new PaoNoodles();
    }
}
