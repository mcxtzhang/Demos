package com.example.mykotlindemo.kuozhanyuyan3

import android.os.Build

/**
 * Created by zhangxutong on 2018/1/2.
 */
//本质上是个函数， 函数接受一个参数，
// 参数是lambda形式定义的函数2：无入参，无返回值。
// 函数体是 判断版本，符合条件调用函数2的函数体
inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}