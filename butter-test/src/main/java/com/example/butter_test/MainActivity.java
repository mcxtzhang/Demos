package com.example.butter_test;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView mTvHint;
    List list = new ArrayList();

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
                //startActivity(new Intent(MainActivity.this, Main2Activity.class));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //while(true){
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
                float allocedMemoK = totalMemoK-freeMemoK;
                float allocedMemoM = totalMemoM-freeMemoM;

                mTvHint.setText("totalMemory = [" + totalMemoK + "K]" + totalMemoM + "M"
                        +"\n freeMemory = [" + freeMemoK + "K]" + freeMemoM + "M"
                        +"\n alloced = [" + allocedMemoK + "K]" + allocedMemoM + "M");
            }
        });

    }
}
