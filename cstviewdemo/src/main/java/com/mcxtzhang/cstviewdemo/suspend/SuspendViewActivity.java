package com.mcxtzhang.cstviewdemo.suspend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mcxtzhang.cstviewdemo.R;

public class SuspendViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend_view);
/*
        ViewGroup vg = (ViewGroup) findViewById(R.id.root);

        vg.addView(new DebugFpsView(this));*/

        View decorView = getWindow().getDecorView();
        decorView = decorView.findViewById(android.R.id.content);
        Log.d("TAG", "onCreate() called with: decorView = [" + decorView + "]");
        if (decorView instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) decorView;
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            frameLayout.addView(new DebugFpsView(this), frameLayout.getChildCount() - 1,lp );
        }
    }
}
