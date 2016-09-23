package com.mcxtzhang.databindingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.databindingdemo.databinding.ActivityTwoBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/


        ActivityTwoBinding  activityTwoBinding = ActivityTwoBinding.inflate(getLayoutInflater());
        activityTwoBinding.setMainPresenter(new MainPresenter(this));
        activityTwoBinding.setTestBean(new TestBean(1,"张旭童"));
        setContentView(activityTwoBinding.getRoot());
    }
}
