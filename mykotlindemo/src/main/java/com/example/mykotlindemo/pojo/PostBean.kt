package com.example.mykotlindemo.pojo

/**
 * 数据类  加上data关键字就成为了数据类 就自动有 toString等方法了
 * 还有 equals   hashCode  copy....
 * Created by zhangxutong on 2017/12/28.
 */
data class PostBean(val who: String, val url: String)