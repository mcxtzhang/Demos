package com.shopcart;

import com.common.IShopCartBean;

import java.util.ArrayList;
import java.util.List;

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

    private int tag;//特殊标记，例如 午餐晚餐    ，早餐的日期，品牌馆的店铺等

    public static int mPrimaryKey;
    public static XYBean mockData() {
        XYBean xyBean = new XYBean();
        xyBean.name = "one";
        xyBean.price = "0.01";
        xyBean.count = 3;
        xyBean.primaryKey = mPrimaryKey++ +"";
        xyBean.desc = "lala";

        xyBean.tag = 2;//午餐
        return xyBean;
    }

    public static XYBean mockData2() {
        XYBean xyBean = new XYBean();
        xyBean.name = "two";
        xyBean.price = "10";
        xyBean.count = 5;
        xyBean.primaryKey = mPrimaryKey++ +"";
        xyBean.desc = "hahha";

        xyBean.tag = 3;//晚餐
        return xyBean;
    }

    public static List<XYBean> mockDatas() {
        List<XYBean> lisst = new ArrayList<>();

        lisst.add(mockData());
        lisst.add(mockData2());
        lisst.add(mockData());
        lisst.add(mockData2());
        lisst.add(mockData());
        lisst.add(mockData2());
        lisst.add(mockData());
        lisst.add(mockData2());
        lisst.add(mockData());
        lisst.add(mockData2());
        return lisst;
    }

    public int getTag() {
        return tag;
    }

    public XYBean setTag(int tag) {
        this.tag = tag;
        return this;
    }

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

    @Override
    public String group() {
        return tag + "";
    }

    @Override
    public long updateTime() {
        return 0;
    }
}
