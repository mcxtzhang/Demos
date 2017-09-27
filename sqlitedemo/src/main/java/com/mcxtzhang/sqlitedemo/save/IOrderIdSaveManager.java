package com.mcxtzhang.sqlitedemo.save;

/**
 * Intro: 订单id保存管理类
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/9/27.
 * History:
 */

public interface IOrderIdSaveManager {
    void saveOrderId(String orderId);

    boolean existOrderId(String orderId);
}
