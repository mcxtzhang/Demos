package anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.AbstractDpFrameMonitor;

/**
 * Created by zhangxutong on 2018/2/5.
 */
@Deprecated
public class SumDurationMonitor extends AbstractDpFrameMonitor<SumDurationFrameListener> {

    private static class InnerHolder {
        static final SumDurationMonitor INSTANCE = new SumDurationMonitor();
    }

    public static SumDurationMonitor getInstance() {
        return InnerHolder.INSTANCE;
    }

    @Override
    public SumDurationFrameListener createFrameListener() {
        return new SumDurationFrameListener();
    }

}
