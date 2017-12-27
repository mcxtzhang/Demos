package com.example.mykotlindemo.bianliangAndshuxing

/**
 * Created by zhangxutong on 2017/12/27.
 */
class JibenLeixing {
    //数字不会自动转型，例如，不能给Double 分配一个Int 必须明确的类型转换
    val i: Int = 7
    val d: Double = i.toDouble()

    //字符不能直接作为一个数字来处理
    val v: Char = 'c'
    val num: Int = v.toInt()

    //位运算也有不同
    val bitwistOr = 1 or 2
    val bitwistAnd = 1 and 2

    val bitwistXor = 0x1 xor 0x1


    //String 可以像数组那样访问，迭代
    val s = "example"
    val c = s[2]//a
    val s2 = "mcxtzhang"

    fun printS() {
        for (item in s2) {
            println(item)
        }
    }
}

