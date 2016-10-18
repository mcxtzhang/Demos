package com.example.abstractt;

import com.example.abstractt.bean.IDrinks;
import com.example.bean.INoodles;

/**
 * 介绍：抽象工厂基类
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public abstract class AbstractFoodFactory {
    /**
     * 生产面条
     *
     * @return
     */
    public abstract INoodles createNoodles();

    /**
     * 生产饮料
     */
    public abstract IDrinks createDrinks();
}
