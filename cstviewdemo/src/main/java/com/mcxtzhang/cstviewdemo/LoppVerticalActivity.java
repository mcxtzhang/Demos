package com.mcxtzhang.cstviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LoppVerticalActivity extends AppCompatActivity {
    TextSwitcher mTextSwitcher;

    Runnable mRunnable = new Runnable() {
        int index = 0;

        @Override
        public void run() {
            mTextSwitcher.setText("" + index++);
            mTextSwitcher.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lopp_vertical);

        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitch);


        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // Create a new TextView
                Log.d("TAG", "run() called");
                TextView t = new TextView(LoppVerticalActivity.this);
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                t.setTextAppearance(LoppVerticalActivity.this, android.R.style.TextAppearance_Large);
                t.setBackgroundColor(Color.BLUE);
                return t;
            }
        });

        mTextSwitcher.postDelayed(mRunnable, 1000);
        mTextSwitcher.setCurrentText("一开始");
        mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_in));
        //mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_out));

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
