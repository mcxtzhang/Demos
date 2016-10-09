package com.example.abstractt;

import com.example.abstractt.bean.IDrinks;
import com.example.abstractt.bean.WaterDrinks;
import com.example.bean.INoodles;
import com.example.bean.LzNoodles;

/**
 * 介绍：兰州拉面工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class LzlmFoodFactory extends AbstractFoodFactory {
    @Override
    public INoodles createNoodles() {
        return new LzNoodles();//卖兰州拉面
    }

    @Override
    public IDrinks createDrinks() {
        return new WaterDrinks();//卖水
    }
}
