package anlaiye.com.cn.performancedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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
                for (int i = 0; i < 1000; i++) {
                    temp = temp + i;
                }

            }
        });

        findViewById(R.id.testAllocationTrackerSb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder("temp");
                for (int i = 0; i < 1000; i++) {
                    sb.append(i);
                }

            }
        });


        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + findViewById(R.id.root).getBackground().getClass() + "]");
    }
}
