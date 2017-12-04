package com.example.butter_test.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.butter_test.R;

public class MidlleJumpActivity extends AppCompatActivity {
    private static final String TAG = LaunchTimeTestActivity.TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midlle_junp);
        Log.d(TAG, "MidlleJumpActivity ::onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        startActivity(new Intent(this, MiddleJumpResultActivity.class));
        Log.d(TAG, "MidlleJumpActivity finish");
        finish();
    }
}
