package com.mcxtzhang.rxjava2demo.lifecycle.base;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/17.
 * History:
 */

public class RxLifecycleUtil {
    private RxLifecycleUtil() {
    }

    public static <T, R> RxLifecycleTransformer<T> bindUntilEvent(final Observable<R> lifecycle, final R lifecycleEvent) {
        return new RxLifecycleTransformer<T>(lifecycle.filter(new Predicate<R>() {
            @Override
            public boolean test(R r) throws Exception {
                return r.equals(lifecycleEvent);
            }
        }));
    }

}
