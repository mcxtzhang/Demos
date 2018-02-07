package anlaiye.com.cn.performancedemo.monitor.framemetrics.instantfps;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */
@Deprecated
public class InstantFpsMonitor extends AbstractDpFrameMonitor<InstantFpsFrameListener> {

    private static class InnerHolder {
        static final InstantFpsMonitor INSTANCE = new InstantFpsMonitor();
    }

    public static InstantFpsMonitor getInstance() {
        return InstantFpsMonitor.InnerHolder.INSTANCE;
    }

    @Override
    public InstantFpsFrameListener createFrameListener() {
        return new InstantFpsFrameListener();
    }
}
