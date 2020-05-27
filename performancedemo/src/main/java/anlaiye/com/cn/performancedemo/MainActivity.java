package anlaiye.com.cn.performancedemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import anlaiye.com.cn.performancedemo.measure.TestMeasureActivity;
import anlaiye.com.cn.performancedemo.measure.TestMeasureLInearActivity;
import anlaiye.com.cn.performancedemo.measure.TestMeasureRelativeActivity;

/**
 * 测试  ● Allocation Tracker：追踪内存对象的来源。
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/Performance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.testAllocationTracker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = "temp";
                for (int i = 0; i < 20000; i++) {
                    temp = temp + i;
                }
                Log.d(TAG, "onClick() called with: temp = [" + temp + "]");

            }
        });

        findViewById(R.id.testAllocationTrackerSb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder("temp");
                for (int i = 0; i < 20000; i++) {
                    sb.append(i);
                }
                Log.d(TAG, "onClick() called with: sb = [" + sb + "]");
            }
        });


        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + findViewById(R.id.root).getBackground().getClass() + "]");


        //以下测试 三个控件哪个性能好，实测 ConstraintLayout 也只调用一次 measure。比RelativeLayout好
        findViewById(R.id.testLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestMeasureLInearActivity.class));
            }
        });

        findViewById(R.id.testRL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestMeasureRelativeActivity.class));
            }
        });
        findViewById(R.id.testCons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestMeasureActivity.class));
            }
        });

        findViewById(R.id.gcInfos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// 运行的GC次数
                String gcCounts = Debug.getRuntimeStat("art.gc.gc-count");
// GC使用的总耗时，单位是毫秒
                String gcTimes = Debug.getRuntimeStat("art.gc.gc-time");
// 阻塞式GC的次数
                String blockGcCounts = Debug.getRuntimeStat("art.gc.blocking-gc-count");
// 阻塞式GC的总耗时
                String blockGcTimes = Debug.getRuntimeStat("art.gc.blocking-gc-time");

                Log.d(TAG, "onClick() called with: 运行的GC次数 = [" + gcCounts + "], GC使用的总耗时:" + gcTimes +
                        ", 阻塞式GC的次数:" + blockGcCounts + ",  阻塞式GC的总耗时:" + blockGcTimes);

            }
        });

    }
}
