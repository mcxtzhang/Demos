package com.mcxtzhang.github.kotlin

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/18.
 * History:
 */
class FirstBean {
    var name: String? = null

    var age: Int? = null

    constructor() {}

    constructor(name: String) {
        this.name = name;
    }

    constructor(name: String, age: Int) {
        this.name = name;
        this.age = age;
    }


}