package com.example.objectadapter;

import com.example.Voltage220;
import com.example.Voltage5;

/**
 * 介绍：对象适配器模式：
 * 持有 src类，实现 dst 类接口，完成src->dst的适配。 。以达到解决**兼容性**的问题。
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/18.
 */

public class VoltageAdapter2 implements Voltage5 {
    private Voltage220 mVoltage220;

    public VoltageAdapter2(Voltage220 voltage220) {
        mVoltage220 = voltage220;
    }

    @Override
    public int output5V() {
        int dst = 0;
        if (null != mVoltage220) {
            int src = mVoltage220.output220V();
            System.out.println("对象适配器工作，开始适配电压");
            dst = src / 44;
            System.out.println("适配完成后输出电压：" + dst);
        }
        return dst;
    }
}
