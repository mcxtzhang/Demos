package com.mcxtzhang.animdemo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.animdemo.databinding.ActivityLauncherBinding;
import com.mcxtzhang.animdemo.vipclub.TaobaoVipAnimActivity;
import com.mcxtzhang.animdemo.ui.WaterWaveActivity;

public class LauncherActivity extends AppCompatActivity {
    ActivityLauncherBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_launcher, null, false);
        setContentView(mBinding.getRoot());
        mBinding.setP(new LauncherPresenter());

        findViewById(R.id.googleNewAnim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, NewAnim20170316Activity.class));
            }
        });



        ValueAnimator colorAnim = ObjectAnimator.ofInt(getWindow().getDecorView(), "backgroundColor", 0xfff8080, 0xff8080ff);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    public class LauncherPresenter {
        public void toWaterWave(View v) {
            startActivity(new Intent(v.getContext(), WaterWaveActivity.class));
        }

        public void taobaoVipAnim(View v) {
            startActivity(new Intent(v.getContext(), TaobaoVipAnimActivity.class));
        }
    }
}
