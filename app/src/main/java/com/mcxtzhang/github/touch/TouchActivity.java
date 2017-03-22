package com.mcxtzhang.github.touch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ZRouter;
import com.mcxtzhang.github.R;

@ZRouter(path = "touch")
public class TouchActivity extends AppCompatActivity {

    private static final String TAG = "zxt/TouchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        Log.d(TAG, "T onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "T onRestart() called");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "T onStart() called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "T onResume() called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "T onPause() called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "T onStop() called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "T onDestroy() called");
        super.onDestroy();
    }
}
