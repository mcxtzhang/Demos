package com.common;

import android.util.Log;

import com.shopcart.XYBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
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
    public void update(final IShopCartBean bean) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate((RealmModel) bean);

            }
        });
    }

    @Override
    public void delete(final IShopCartBean bean) {
        XYBean toBeDelBean = (XYBean) bean;
        delete(toBeDelBean.getPrimaryKey());
    }

    @Override
    public void delete(List<? extends IShopCartBean> datas) {
        if (null != datas && !datas.isEmpty()) {
            for (IShopCartBean data : datas) {
                delete(data);
            }
        }
    }

    @Override
    public void delete(final String goodsId) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<XYBean> all = realm.where(XYBean.class)
                        .equalTo("primaryKey", goodsId)
                        .findAll();
                if (!all.isEmpty()) {
                    all.deleteAllFromRealm();
                }
            }
        });
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
                all = all.sort("tag", Sort.ASCENDING, "primaryKey", Sort.DESCENDING);
                return (List<T>) mRealm.copyFromRealm(all);
            default:
                return null;
        }
    }

    @Override
    public void close() {
        mRealm.close();
    }

    @Override
    public void clearAll() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<XYBean> allAsync = realm.where(XYBean.class).findAllAsync();
                RealmChangeListener<RealmResults<XYBean>> realmChangeListener = new RealmChangeListener<RealmResults<XYBean>>() {
                    @Override
                    public void onChange(RealmResults<XYBean> element) {
                        Log.d("TAG", "onChange() called with: element = [" + element + "]");
                        if (element != null) {
                            // do sth
                            allAsync.removeAllChangeListeners();
                        }
                    }
                };
                allAsync.addChangeListener(realmChangeListener);
            }
        });


    }
}
