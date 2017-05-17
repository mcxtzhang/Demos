package com.common;

import java.util.List;

/**
 * Intro: 购物车管理类
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/12.
 * History:
 */
public interface ShopCartManager {
    void update(IShopCartBean bean);

    void delete(IShopCartBean bean);

    void delete(List<? extends IShopCartBean> datas);

    void delete(String goodsId);

    <T> List<T> select(int busiType);

    void close();

    void clearAll();
}
