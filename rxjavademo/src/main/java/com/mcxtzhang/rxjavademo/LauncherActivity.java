package com.mcxtzhang.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.rxjavademo.async.MockAsyncActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btnDemo).setOnClickListener(v -> startActivity(new Intent(LauncherActivity.this, MainActivity.class)));
        findViewById(R.id.btnAync).setOnClickListener(v -> startActivity(new Intent(LauncherActivity.this, MockAsyncActivity.class)));
    }
}
