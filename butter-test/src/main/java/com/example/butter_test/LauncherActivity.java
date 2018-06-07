package com.example.butter_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.butter_test.launch.LaunchTimeTestActivity;
import com.example.butter_test.meminfo.badcase.ThreadLeakActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btnLaunchTimeMonitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, LaunchTimeTestActivity.class));
            }
        });

        findViewById(R.id.btnThreadLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ThreadLeakActivity.class));
            }
        });

    }
}
