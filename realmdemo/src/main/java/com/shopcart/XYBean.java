package com.shopcart;

import com.common.IShopCartBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/13.
 * History:
 */
public class XYBean extends RealmObject implements IShopCartBean {

    private int count;

    private String name;
    private String price;
    @PrimaryKey
    private String primaryKey;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public XYBean setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getCount() {
        return count;
    }

    public XYBean setCount(int count) {
        this.count = count;
        return this;
    }

    public String getName() {
        return name;
    }

    public XYBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public XYBean setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public XYBean setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String price() {
        return price;
    }

    @Override
    public String id() {
        return primaryKey;
    }
}
