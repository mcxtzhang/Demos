package anlaiye.com.cn.performancedemo.monitor.framemetrics;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Window;

/**
 * Created by zhangxutong on 2018/2/6.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public interface IFrameListener extends Window.OnFrameMetricsAvailableListener {

    double getAvgFpsInSeconds();

    double getMinFpsInSeconds();
}
