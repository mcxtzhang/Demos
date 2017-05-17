package com.mcxtzhang.rxjava2demo.lifecycle.base;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Intro: 如果另一个数据源（生命周期符合）发送了数据，则停止发送第一个Observable的数据
 * 暂时只支持 Observable、Single 后续用到再添加
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/17.
 * History:
 */

public final class RxLifecycleTransformer<T> implements ObservableTransformer<T, T>,
        SingleTransformer<T, T> {

    final Observable<?> lifecycleObservable;

    public RxLifecycleTransformer(Observable<?> lifecycleObservable) {
        this.lifecycleObservable = lifecycleObservable;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.takeUntil(lifecycleObservable);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.takeUntil(lifecycleObservable.firstOrError());
    }
}
