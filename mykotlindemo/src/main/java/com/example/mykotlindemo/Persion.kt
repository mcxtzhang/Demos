package com.example.mykotlindemo

import android.app.Activity
import android.widget.Toast

/**
 * Created by zhangxutong on 2017/12/27.
 */
class Persion(name: String, surname: String) {
    init {
        println("我是构造函数init{}")
    }
}


//类继承

open class Animal(name: String)

class Cat(name: String, age: Int) : Animal(name) {
    fun add(x: Int, y: Int): Int {
        return x + y
    }

    //如果返回的结果 可以使用一个表达式计算出来 ， 可以不使用括号 而使用等号：
    fun add2(x: Int, y: Int): Int = x + y


    //构造方法和函数参数
    // 我们可以给参数指定一个默认值使得它们变得可选
    fun toast(activity: Activity, msg: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, msg, length)
    }

    //更复杂的例子：
    fun niceToast(activity: Activity, msg: String, tag: String = "sth", length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(activity, "[$tag]$msg", length).show()

        //String模板，使用$可以插入一个表达式,如果表达式复杂 用花括号括起来:${user.name}
    }

    fun callNiceToast(activity: Activity) {
        niceToast(activity, "A")
        niceToast(activity, "A", "tag")
        niceToast(activity, "A", "tag", Toast.LENGTH_LONG)

        //还可以用函数名称来调用
        niceToast(activity, "A", length = Toast.LENGTH_LONG)
    }
}