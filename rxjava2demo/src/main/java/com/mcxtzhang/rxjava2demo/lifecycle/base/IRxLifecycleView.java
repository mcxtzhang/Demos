package com.mcxtzhang.rxjava2demo.lifecycle.base;

/**
 * Intro: View 层，暴露的 RxLifecycle相关的方法
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/17.
 * History:
 */

public interface IRxLifecycleView {
    //和指定的Fragment声明周期绑定
    <T> RxLifecycleTransformer<T> bindUntilFragmentEvent(FragmentEvent fragmentEvent);

    //和Fragment的Detach绑定
    <T> RxLifecycleTransformer<T> bindToFragmentDetach();
}
