package com.example.classadapter;

import com.example.Voltage220;
import com.example.Voltage5;

/**
 * 介绍：Adapter类：完成220V-5V的转变
 * 通过继承src类，实现 dst 类接口，完成src->dst的适配。
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/18.
 */

public class VoltageAdapter extends Voltage220 implements Voltage5 {
    @Override
    public int output5V() {
        int src = output220V();
        System.out.println("适配器工作开始适配电压");
        int dst = src / 44;
        System.out.println("适配完成后输出电压：" + dst);
        return dst;
    }
}
