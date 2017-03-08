package com.mcxtzhang;

import java.util.HashMap;
import java.util.Map;

/**
 * 介绍：跳转规则保存类
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2017/3/9.
 */
public class Rules {
    private static Map<String, String> routerMap = new HashMap<>();

    public static void put(String path, String classPath) {
        routerMap.put(path, classPath);
    }

    public static void putAll(Map<String, String> childMap) {
        routerMap.putAll(childMap);
    }

    public static Map<String, String> getRouterMap() {
        AModuleRouter
        return routerMap;
    }
}
