package anlaiye.com.cn.performancedemo.monitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import anlaiye.com.cn.performancedemo.R;
import anlaiye.com.cn.performancedemo.monitor.list.RvActivity;

public class MonitorActivity extends AppCompatActivity {
    private static final String TAG = "MonitorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        //monitorMainLooper();
        //PerformanceMonitorUtils.monitorChoreoGrapher();
        //PerformanceMonitorUtils.monitorFrameMetrics(this);
        //new ActivityFrameMetrics.Builder().build().startFrameMetrics(this);
        /**
         * 换句话说，IdleHandler就是 优先级别较低的 Message，
         * 只有当 Looper 没有消息要处理时才得到处理。
         * 而且，内部的 queueIdle() 方法若返回 true，表示该任务一直存活，
         * 每次 Looper 进入 Idle 时就执行；反正，如果返回 false，则表示只会执行一次，执行完后丢弃。
         */
        /*Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.e("TAG", "queueIdle: 现在空了");
                return true;
            }
        });*/
        findViewById(R.id.btnJumpA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MonitorActivity.this, RvActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        Log.w("TAG", "onResume() called");
        super.onResume();
        Log.e("TAG", "onResume() called");
    }



}
