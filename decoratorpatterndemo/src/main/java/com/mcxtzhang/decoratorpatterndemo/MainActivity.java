package com.mcxtzhang.decoratorpatterndemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "zxt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "dasdas", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "普通点击:" + System.currentTimeMillis());
            }
        });

        findViewById(R.id.btnProDouble).setOnClickListener(new OnProDoubleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "防止多次点击:" + System.currentTimeMillis());
            }
        }));

    }

}
