package com.mcxtzhang.rxjava2demo.lifecycle.base.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.rxjava2demo.lifecycle.base.FragmentEvent;
import com.mcxtzhang.rxjava2demo.lifecycle.base.IRxLifecycleView;
import com.mcxtzhang.rxjava2demo.lifecycle.base.RxLifecycleTransformer;
import com.mcxtzhang.rxjava2demo.lifecycle.base.RxLifecycleUtil;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Intro: View 层职责：
 * 1 发送出生命周期事件
 * 2 提供一个便利bind方法(IRxLifecycleView 接口)供P层去调用
 * <p>
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/17.
 * History:
 */

public class CstRxLifeActivity extends AppCompatActivity implements IRxLifecycleView {
    protected final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(FragmentEvent.DETACH);
    }


    @Override
    public <T> RxLifecycleTransformer<T> bindUntilFragmentEvent(FragmentEvent fragmentEvent) {
        return RxLifecycleUtil.bindUntilEvent(lifecycleSubject, fragmentEvent);
    }

    @Override
    public <T> RxLifecycleTransformer<T> bindToFragmentDetach() {
        return RxLifecycleUtil.bindUntilEvent(lifecycleSubject, FragmentEvent.DETACH);
    }



}
