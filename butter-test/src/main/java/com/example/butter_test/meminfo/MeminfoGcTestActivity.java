package com.example.butter_test.meminfo;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.butter_test.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MeminfoGcTestActivity extends AppCompatActivity {
    private static final String TAG = "butter-test";
    TextView mTvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvHint = (TextView) findViewById(R.id.tvHint);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dumpMemoInfo();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MeminfoTestActivity.this, Main2Activity.class));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //while(true){
                        List list = new ArrayList();
                        list.add(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.background))
                        );
                        dumpMemoInfo();
                        //}

                    }
                }).start();
            }
        });
    }

    private void dumpMemoInfo() {
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                float totalMemoK = totalMemory / 1024f;
                float totalMemoM = totalMemoK / 1024f;
                float freeMemoK = freeMemory / 1024f;
                float freeMemoM = freeMemoK / 1024f;
                float allocedMemoK = totalMemoK - freeMemoK;
                float allocedMemoM = totalMemoM - freeMemoM;

                mTvHint.setText("totalMemory = [" + formatFLoat(totalMemoK) + "K]" + formatFLoat(totalMemoM) + "M"
                        + "\n freeMemory = [" + formatFLoat(freeMemoK) + "K]" + formatFLoat(freeMemoM) + "M"
                        + "\n alloced = [" + formatFLoat(allocedMemoK) + "K]" + formatFLoat(allocedMemoM) + "M");

                try {
                    Process exec = Runtime.getRuntime().exec("adb shell dumpsys meminfo 'com.example.butter_test' |grep 'Dalvik Heap'");
                    Log.d(TAG, "run() called:"+exec);
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
