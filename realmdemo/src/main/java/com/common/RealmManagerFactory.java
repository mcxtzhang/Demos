package com.common;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/12.
 * History:
 */
public class RealmManagerFactory implements ShopCartManagerFactory {
    @Override
    public ShopCartManager create() {
        return new RealmManager();
    }
}
