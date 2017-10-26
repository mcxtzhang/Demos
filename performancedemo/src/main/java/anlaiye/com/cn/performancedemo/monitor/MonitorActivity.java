package anlaiye.com.cn.performancedemo.monitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;

import anlaiye.com.cn.performancedemo.R;

public class MonitorActivity extends AppCompatActivity {
    private static final String TAG = "MonitorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastTime;

            @Override
            public void doFrame(long frameTimeNanos) {
                long gap = ((frameTimeNanos - lastTime) / 1000000);
                Log.d(TAG, "doFrame() called with: frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap);
                if (gap>16){
                    long count = (gap-16) / 16 ;
                    Log.e(TAG, "doFrame()丢帧 called with: frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap+",丢了几帧:"+count);

                }
                lastTime = frameTimeNanos;
                Choreographer.getInstance().postFrameCallback(this);
            }
        });

    }
}
