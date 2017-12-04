package com.example.butter_test.launch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.butter_test.R;

public class MiddleJumpResultActivity extends AppCompatActivity {
    private static final String TAG = LaunchTimeTestActivity.TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_jump_result);
        Log.d(TAG, "MiddleJumpResultActivity onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }
}
