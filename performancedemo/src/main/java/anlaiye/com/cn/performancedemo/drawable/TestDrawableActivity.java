package anlaiye.com.cn.performancedemo.drawable;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import anlaiye.com.cn.performancedemo.R;

public class TestDrawableActivity extends AppCompatActivity {

    ArrayList<Bitmap> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drawable_1);

        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();
        Log.d("TAG", "onCreate() called with:         memoryInfo.availMem\n = [" + memoryInfo.availMem + "]");
        Log.d("TAG", "onCreate() called with:         memoryInfo.totalMem\n = [" + memoryInfo.totalMem + "]");
        Log.d("TAG", "onCreate() called with:         memoryInfo.lowMemory\n = [" + memoryInfo.lowMemory + "]");
        Log.d("TAG", "onCreate() called with:         memoryInfo.threshold\n = [" + memoryInfo.threshold + "]");


        findViewById(R.id.btnClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 500; i++) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading_only_xxh);
                    list.add(bitmap);
                }
                ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();
                Log.d("TAG", "onCreate() called with:         memoryInfo.availMem\n = [" + memoryInfo.availMem + "]");
                Log.d("TAG", "onCreate() called with:         memoryInfo.totalMem\n = [" + memoryInfo.totalMem + "]");
                Log.d("TAG", "onCreate() called with:         memoryInfo.lowMemory\n = [" + memoryInfo.lowMemory + "]");
                Log.d("TAG", "onCreate() called with:         memoryInfo.threshold\n = [" + memoryInfo.threshold + "]");
            }
        });
    }


    private ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}
