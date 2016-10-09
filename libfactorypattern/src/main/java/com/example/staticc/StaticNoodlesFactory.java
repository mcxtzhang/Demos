package com.example.staticc;

import com.example.bean.GankouNoodles;
import com.example.bean.INoodles;
import com.example.bean.LzNoodles;
import com.example.bean.PaoNoodles;

/**
 * 介绍：静态工厂（个人认为是）
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/9.
 */

public class StaticNoodlesFactory {
    /**
     * 传入Class实例化面条产品类
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T extends INoodles> T createNoodles(Class<T> clz) {
        T result = null;
        try {
            result = (T) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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
