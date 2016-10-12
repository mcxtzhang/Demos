package com.example.staticc;

import com.example.bean.INoodles;

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
}
