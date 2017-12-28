package com.example.mykotlindemo.domain

/**
 * Created by zhangxutong on 2017/12/28.
 */
public interface Command<T> {
    fun execute(): T
}