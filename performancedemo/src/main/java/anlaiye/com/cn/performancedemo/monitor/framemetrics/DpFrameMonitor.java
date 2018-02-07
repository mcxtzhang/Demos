package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import java.util.HashMap;
import java.util.Map;

import anlaiye.com.cn.performancedemo.monitor.framemetrics.instantfps.InstantFpsFrameListener;
import anlaiye.com.cn.performancedemo.monitor.framemetrics.sumduration.SumDurationFrameListener;

/**
 * Created by zhangxutong on 2018/2/6.
 */

public class DpFrameMonitor<T extends IFrameListener> extends AbstractDpFrameMonitor<T> {
    Class<T> mListenerClass;

    private DpFrameMonitor(Class<T> aClass) {
        mListenerClass = aClass;
    }

    private static Map<String, DpFrameMonitor> mCache = new HashMap<>();

    public static DpFrameMonitor getInstance(String type) {
        if (mCache.get(type) == null) {
            synchronized (DpFrameMonitor.class) {
                if (mCache.get(type) == null) {
                    if ("instantFps".equals(type)) {
                        mCache.put(type, new DpFrameMonitor(InstantFpsFrameListener.class));
                    } else if ("sumDuration".equals(type)) {
                        mCache.put(type, new DpFrameMonitor(SumDurationFrameListener.class));
                    } else {
                        throw new RuntimeException("FrameListener type error!");
                    }
                }
            }
        }
        return mCache.get(type);
    }

    @Override
    public T createFrameListener() {
        try {
            return mListenerClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
