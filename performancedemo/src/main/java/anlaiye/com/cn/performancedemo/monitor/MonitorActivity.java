package anlaiye.com.cn.performancedemo.monitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import anlaiye.com.cn.performancedemo.R;

import static anlaiye.com.cn.performancedemo.monitor.PerformanceMonitorUtils.monitorChoreoGrapher;

public class MonitorActivity extends AppCompatActivity {
    private static final String TAG = "MonitorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        monitorChoreoGrapher();

    }
}
