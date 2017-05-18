package com.mcxtzhang.github.kotlin

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/5/18.
 * History:
 */

class ThridBean {
    override fun toString(): String {
        return "ThridBean{" +
                "name=??????????????'" + name + '\'' +
                ", age=" + age +
                ", price=" + price +
                '}'
    }

    internal var name: String? = null
    internal var age: Int = 0
    internal var price: Double = 0.toDouble()

    fun getName(): String? {
        return name
    }

    fun setName(name: String): ThridBean {
        this.name = name
        return this
    }

    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int): ThridBean {
        this.age = age
        return this
    }

    fun getPrice(): Double {
        return price
    }

    fun setPrice(price: Double): ThridBean {
        this.price = price
        return this
    }
}
