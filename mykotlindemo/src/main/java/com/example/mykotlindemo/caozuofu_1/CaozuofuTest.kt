package com.example.mykotlindemo.caozuofu_1

/**
 * Created by zhangxutong on 2017/12/29.
 */
public class Operation(val data: String) {
    public operator fun get(position: Int): Char {
        return data[position]
    }
}

