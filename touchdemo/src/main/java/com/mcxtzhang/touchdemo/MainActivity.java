package com.mcxtzhang.touchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        findViewById(R.id.customView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                v.requestLayout();
            }
        });
        findViewById(R.id.customView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "long", Toast.LENGTH_SHORT).show();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height= 300;
                layoutParams.width = 600;
                v.setLayoutParams(layoutParams);
                v.invalidate();
                return true;
            }
        });*/
/*        Map<String, String> map = new ArrayMap<>();
        map.put("1","1");
        map.put(null,"2");
        map.put("3",null);
        map.put("6",null);
        map.put("5",null);
        map.put("4",null);
        Log.e("TAG", "onCreate() called with: map = [" + map + "]");*/
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent() called with: ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent() called with: event = [" + event + "]");
        return super.onTouchEvent(event);
    }
}
