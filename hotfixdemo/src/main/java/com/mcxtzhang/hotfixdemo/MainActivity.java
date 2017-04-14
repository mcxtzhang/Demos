package com.mcxtzhang.hotfixdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/HotFix";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader = MainActivity.class.getClassLoader();

        while (classLoader != null) {
            Log.d(TAG, "onCreate() called with: classLoader = [" + classLoader + "]");
            classLoader = classLoader.getParent();
        }

        ;
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LeakActivity.class));
            }
        });
    }

}
