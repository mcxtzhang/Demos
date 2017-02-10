package com.mcxtzhang.rxjava2demo.retrofit.removewrapper.rookie;

import com.mcxtzhang.rxjava2demo.retrofit.model.bf.BaseBean;
import com.mcxtzhang.rxjava2demo.retrofit.removewrapper.bendan.RemoveWrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by Administrator on 2017/2/10.
 */

public class RxHelper {
    public static <T> ObservableTransformer<BaseBean<T>, T> helper() {
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                return upstream.map(new RemoveWrapper<T>());
            }
        };
    }

}