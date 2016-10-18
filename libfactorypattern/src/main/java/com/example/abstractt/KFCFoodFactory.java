package com.example.abstractt;

import com.example.abstractt.bean.ColaDrinks;
import com.example.abstractt.bean.IDrinks;
import com.example.bean.INoodles;
import com.example.bean.PaoNoodles;

/**
 * 介绍：肯德基工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class KFCFoodFactory extends AbstractFoodFactory {
    @Override
    public INoodles createNoodles() {
        return new PaoNoodles();//KFC居然卖泡面
    }

    @Override
    public IDrinks createDrinks() {
        return new ColaDrinks();//卖可乐
    }
}
