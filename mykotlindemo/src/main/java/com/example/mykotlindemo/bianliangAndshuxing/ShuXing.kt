package com.example.mykotlindemo.bianliangAndshuxing

/**
 * Created by zhangxutong on 2017/12/27.
 */
class ShuXing {
    var name: String = ""
        get() = field.toUpperCase()
        set(value) {
            field = "Name:$value"
        }
}

