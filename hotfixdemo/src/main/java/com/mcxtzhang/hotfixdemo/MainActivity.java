package com.mcxtzhang.hotfixdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/HotFix";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader = MainActivity.class.getClassLoader();

        while (classLoader!=null){
            Log.d(TAG, "onCreate() called with: classLoader = [" + classLoader + "]");
            classLoader = classLoader.getParent();
        }
    }
}
