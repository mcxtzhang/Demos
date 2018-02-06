package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.instantfps.InstantFpsMonitor;
import anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration.SumDurationMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class ButterFactory {
    public static IFrameMonitor getFrameMonitor() {
        if (true) {
            return SumDurationMonitor.getInstance();
        } else {
            return getInstantFpsMonitor();
        }
    }

    public static IFrameMonitor getInstantFpsMonitor() {
        return InstantFpsMonitor.getInstance();
    }
}
