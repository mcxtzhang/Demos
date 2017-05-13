package com.common;

import com.shopcart.XYBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Intro: 购物车管理 具体实现类 可以和业务耦合
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/12.
 * History:
 */
public class RealmManager implements ShopCartManager {

    Realm mRealm;

    public RealmManager() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void update(IShopCartBean bean) {
        mRealm.beginTransaction();
        mRealm.insertOrUpdate((RealmModel) bean);
        mRealm.commitTransaction();
    }

    @Override
    public void delete(IShopCartBean bean) {

    }

    @Override
    public <T> List<T> select(int busiType) {
        switch (busiType) {
            case BusiType.TYPE_XIYOU_FOODS:
                RealmResults<XYBean> all = mRealm.where(XYBean.class)
                        /*.beginGroup()
                        .equalTo("tag", 1)
                        .endGroup()*/
                        .findAll();
                all = all.sort("tag", Sort.ASCENDING , "primaryKey",Sort.DESCENDING);
                return (List<T>) all;
            default:
                return null;
        }
    }
}
