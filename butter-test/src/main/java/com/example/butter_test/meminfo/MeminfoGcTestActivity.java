package com.example.butter_test.meminfo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.butter_test.R;
import com.example.butter_test.cpu.CpuMonitorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MeminfoGcTestActivity extends AppCompatActivity {
    private static final String TAG = "butter-test";
    TextView mTvHint;
    List list = new ArrayList();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long debugCpuValue = CpuMonitorUtils.INSTANCE.endMonitor("MeminfoGcTestActivity");
        Log.w(TAG, "onDestroy() end with: cpu:" + debugCpuValue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CpuMonitorUtils.INSTANCE.startMonitor("MeminfoGcTestActivity");


        mTvHint = (TextView) findViewById(R.id.tvHint);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CpuMonitorUtils.INSTANCE.startMonitor("button");
                dumpMemoInfo();
                long debugCpuValue = CpuMonitorUtils.INSTANCE.endMonitor("button");
                Log.d(TAG, "onClick() end with: cpu:" + debugCpuValue);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MeminfoTestActivity.this, Main2Activity.class));

                new Thread(new Runnable() {


                    @Override
                    public void run() {
                        //while (true) {
                        list.add(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.background))
                        );
                        list.add(new String[500000]);
                        dumpMemoInfo();
                        //}

                    }
                }).start();
            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    //Log.d(TAG, "run() called:"+Debug.getPss());
//                    Debug.getPss();
//                    Runtime.getRuntime().totalMemory();
//                    Runtime.getRuntime().freeMemory();
//                }
//
//            }
//        }).start();


        findViewById(R.id.btnRuntime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick() called with: view1 = [" + view + "]");
                CpuMonitorUtils.INSTANCE.startMonitor("btnRuntime");

                long mem = 0;
                long time1 = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                }
                long time2 = System.currentTimeMillis() - time1;
                long debugCpuValue = CpuMonitorUtils.INSTANCE.endMonitor("btnRuntime");
                Log.d(TAG, "onClick() end with: time2 = [" + time2 + "],mem:" + mem + ",cpu:" + debugCpuValue);

            }
        });

        findViewById(R.id.btnDebugPss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick() called with: view2 = [" + view + "]");

                CpuMonitorUtils.INSTANCE.startMonitor("debugPss");

                long mem = 0;
                long time1 = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    mem = Debug.getPss();
                }
                long time2 = System.currentTimeMillis() - time1;
                long debugPssCpuValue = CpuMonitorUtils.INSTANCE.endMonitor("debugPss");
                Log.d(TAG, "onClick() end with: time2 = [" + time2 + "],mem:" + mem + ",cpu:" + debugPssCpuValue);
            }
        });

        findViewById(R.id.btnAmPss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick() called with: view3 = [" + view + "]");
                CpuMonitorUtils.INSTANCE.startMonitor("btnAmPss");
                long mem = 0;
                long time1 = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{android.os.Process.myPid()});
                    mem = memoryInfos[0].getTotalPss();
                }
                long time2 = System.currentTimeMillis() - time1;
                long debugCpuValue = CpuMonitorUtils.INSTANCE.endMonitor("btnAmPss");
                Log.d(TAG, "onClick() end with: time2 = [" + time2 + "],mem:" + mem + ",cpu:" + debugCpuValue);
            }
        });
    }

    private void dumpMemoInfo() {
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                long maxMemory = Runtime.getRuntime().maxMemory();
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                float totalMemoK = totalMemory / 1024f;
                float totalMemoM = totalMemoK / 1024f;
                float freeMemoK = freeMemory / 1024f;
                float freeMemoM = freeMemoK / 1024f;
                float allocedMemoK = totalMemoK - freeMemoK;
                float allocedMemoM = totalMemoM - freeMemoM;
                float maxMemoryK = maxMemory / 1024f;
                float maxMemoryM = maxMemoryK / 1024f;

                long pss = Debug.getPss();
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                float totalPssK = memoryInfo.getTotalPss();
                float totalPssM = totalPssK / 1024f;


                mTvHint.setText("maxMemory = [" + formatFLoat(maxMemoryK) + "K]" + formatFLoat(maxMemoryM) + "M"
                        + "\n totalMemory = [" + formatFLoat(totalMemoK) + "K]" + formatFLoat(totalMemoM) + "M"
                        + "\n freeMemory = [" + formatFLoat(freeMemoK) + "K]" + formatFLoat(freeMemoM) + "M"
                        + "\n alloced = [" + formatFLoat(allocedMemoK) + "K]" + formatFLoat(allocedMemoM) + "M"
                        + "\n totalPss = [" + formatFLoat(totalPssK) + "K]" + formatFLoat(totalPssM) + "M"
                        + "\n pss = [" + pss + "K]" + pss + "M");

                try {
                    Process exec = Runtime.getRuntime().exec("adb shell dumpsys meminfo 'com.example.butter_test' |grep 'Dalvik Heap'");
                    Log.d(TAG, "run() called:" + exec);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String formatFLoat(float num) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(num);//format 返回的是字符串
        return p;
    }


    // Executes UNIX command.
    private String exec(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
