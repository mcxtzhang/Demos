package anlaiye.com.cn.performancedemo.monitor.framemetrics;

/**
 * Created by zhangxutong on 2018/2/5.
 */

public class ButterFactory {
    public static IFrameMonitor getFrameMonitor() {
        if (true) {
            return getSumDurationMonitor();
        } else {
            return getInstantFpsMonitor();
        }
    }

    public static IFrameMonitor getSumDurationMonitor() {
        //return SumDurationMonitor.getInstance();
        return DpFrameMonitor.getInstance("sumDuration");
    }

    public static IFrameMonitor getInstantFpsMonitor() {
        //return InstantFpsMonitor.getInstance();
        return DpFrameMonitor.getInstance("instantFps");
    }
}
