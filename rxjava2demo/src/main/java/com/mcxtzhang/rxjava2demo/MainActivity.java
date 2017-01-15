package com.mcxtzhang.rxjava2demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.rxjava2demo.retrofit.RetrofitDemoActivity;
import com.mcxtzhang.rxjava2demo.rxjava2.Rx2Activity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnRx2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Rx2Activity.class));
            }
        });
        findViewById(R.id.btnRetrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RetrofitDemoActivity.class));
            }
        });
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + getWindow() + "]");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called + getWindow()"+ getWindow());
    }
}
