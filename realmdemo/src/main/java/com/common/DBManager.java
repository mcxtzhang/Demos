package com.common;

/**
 * Intro: 数据库管理类， 封装 从  抽象工厂 里 获取 实际工厂的过程。
 * 对外获取数据库实例唯一的入口。
 * <p>
 * 当切换数据库实现时，只需要修改这个类即可。
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/14.
 * History:
 */
public class DBManager {
    public static ShopCartManager getShopCartManager() {
        return new RealmManagerFactory().create();
    }
}
