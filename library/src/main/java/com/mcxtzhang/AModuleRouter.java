package com.mcxtzhang;

/**
 * 介绍：created by apt
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/3/9.
 */
public class AModuleRouter {
    public static void init() {
        Rules.put("rx", "com.mcxtzhang.github.RxActivity");
        Rules.put("zxt/SparseArray", "com.mcxtzhang.github.SparseArrayOrderTestActivity");
    }
}
