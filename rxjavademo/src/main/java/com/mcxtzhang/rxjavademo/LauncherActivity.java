package com.mcxtzhang.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.rxjavademo.async.MockAsyncActivity;
import com.mcxtzhang.rxjavademo.network.NetworkActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.btnDemo).setOnClickListener(v -> startActivity(new Intent(LauncherActivity.this, MainActivity.class)));
        findViewById(R.id.btnAync).setOnClickListener(v -> startActivity(new Intent(LauncherActivity.this, MockAsyncActivity.class)));
        findViewById(R.id.btnNetwork).setOnClickListener(a -> startActivity(new Intent(a.getContext(), NetworkActivity.class)));


    }
}
