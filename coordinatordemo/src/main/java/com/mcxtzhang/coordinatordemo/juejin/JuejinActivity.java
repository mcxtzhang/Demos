package com.mcxtzhang.coordinatordemo.juejin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.mcxtzhang.coordinatordemo.R;

public class JuejinActivity extends AppCompatActivity {
    NestedScrollView mNestedScrollView;
    LinearLayout mLLBottom;
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juejin);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestSv);
        mLLBottom = (LinearLayout) findViewById(R.id.bottomLayout);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


    }
}
