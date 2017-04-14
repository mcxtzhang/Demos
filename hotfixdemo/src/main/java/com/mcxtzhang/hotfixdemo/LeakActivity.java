package com.mcxtzhang.hotfixdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

public class LeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);


        onLeakClick();


    }



    /**
     * 下面的是一个典型的内存泄露实例：
     * 即一个非静态内部类Handler中有未来得及处理或者延时处理的消息，
     * 但是此时该Activity退出了，并没有移除Handler中未处理的消息，会造成内存泄露。
     * 解决方法是，
     * 一:将此Handler改写为静态内部类,并使用WeakReference弱引用来引用Activity对象。
     * 二(推荐)：在Activity的onDestroy()方法里移除未处理的消息。
     * 原因是：非静态内部类会持有外部类的引用：
     * 持有关系大致为：Looper-MessageQueue-Message-Handler-Activity
     */
    private static final int MSG_LEAK_DELAY = 1;
    private Handler mLeakHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //do sth
            Log.d("zxt", "handleMessage() called with: " + "msg = [" + msg + "]");
        }
    };

    public void onLeakClick() {
        Toast.makeText(this, "已经成功产生一个内存泄露，现在退出此Activity试试看。", Toast.LENGTH_SHORT).show();
        mLeakHandler.sendEmptyMessageDelayed(MSG_LEAK_DELAY, 1000000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplicatioin.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
