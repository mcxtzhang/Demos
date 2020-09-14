package anlaiye.com.cn.performancedemo.drawable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import anlaiye.com.cn.performancedemo.R;

public class DumpBitmapActivity extends AppCompatActivity {
    Handler mTestLeakHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump_bitmap);

        mTestLeakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run() called");
            }
        }, 1000 * 30);
    }
}
