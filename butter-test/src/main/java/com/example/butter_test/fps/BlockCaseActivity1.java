package com.example.butter_test.fps;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.butter_test.R;

public class BlockCaseActivity1 extends AppCompatActivity {

    private static final String TAG = "butter-test/fps/block";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_case1);

        findViewById(R.id.btnBlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (true) {
                    SystemClock.sleep(100);
                    Log.d(TAG, "onClick() called with: view = [" + view + "]");
                }
            }
        });
    }
}
