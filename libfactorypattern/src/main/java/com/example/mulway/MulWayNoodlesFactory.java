package com.example.mulway;

import com.example.bean.GankouNoodles;
import com.example.bean.INoodles;
import com.example.bean.LzNoodles;
import com.example.bean.PaoNoodles;

/**
 * 介绍：多方法静态工厂
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/12.
 */

public class MulWayNoodlesFactory {

    /**
     * 模仿Executors 类
     * 生产泡面
     *
     * @return
     */
    public static INoodles createPm() {
        return new PaoNoodles();
    }

    /**
     * 模仿Executors 类
     * 生产兰州拉面
     *
     * @return
     */
    public static INoodles createLz() {
        return new LzNoodles();
    }

    /**
     * 模仿Executors 类
     * 生产干扣面
     *
     * @return
     */
    public static INoodles createGk() {
        return new GankouNoodles();
    }
}
