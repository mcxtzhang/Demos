package com.mcxtzhang.animdemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.animdemo.databinding.ActivityColorBinding;

public class ColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityColorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_color);


        final ValueAnimator anim = ValueAnimator.ofObject(new ArgbEvaluator(), Color.GREEN, Color.TRANSPARENT);
        binding.activityColor.post(new Runnable() {
            @Override
            public void run() {
                anim.start();
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final int color;
                color = (int) valueAnimator.getAnimatedValue();
                binding.activityColor.setBackgroundColor(color);
            }
        });

        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setDuration(5000);
    }
}
