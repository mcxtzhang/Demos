package com.example.butter_test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    //public static  Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //mContext =this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 100000000);
    }
}
