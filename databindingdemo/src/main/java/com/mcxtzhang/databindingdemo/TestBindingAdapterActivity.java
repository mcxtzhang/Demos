package com.mcxtzhang.databindingdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.databindingdemo.databinding.ActivityTestBindingAdapterBinding;

public class TestBindingAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBindingAdapterBinding binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_test_binding_adapter,null,false);
        long time = 1465801269000L;
        binding.setData(time);

        setContentView(binding.getRoot());

    }
}
