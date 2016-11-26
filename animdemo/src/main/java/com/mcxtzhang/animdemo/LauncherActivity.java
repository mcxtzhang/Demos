package com.mcxtzhang.animdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mcxtzhang.animdemo.databinding.ActivityLauncherBinding;
import com.mcxtzhang.animdemo.ui.WaterWaveActivity;

public class LauncherActivity extends AppCompatActivity {
    ActivityLauncherBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_launcher, null, false);
        setContentView(mBinding.getRoot());
        mBinding.setP(new LauncherPresenter());
    }

    public class LauncherPresenter {
        public void toWaterWave(View v) {
            startActivity(new Intent(v.getContext(), WaterWaveActivity.class));
        }
    }
}
