package com.mcxtzhang.databindingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mcxtzhang.databindingdemo.databinding.ActivityTwoWayBindingBinding;

public class TwoWayActivity extends AppCompatActivity {

    private static final String TAG = "zxt/TwoWayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTwoWayBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_binding);

        final TestBean testBean = new TestBean(1, "张旭童");

        testBean.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d(TAG, "value now??" + testBean.getName());
                Log.d(TAG, "onPropertyChanged() called with: sender = [" + sender + "], propertyId = [" + propertyId + "]");
            }
        });
        binding.setBean(testBean);

    }
}
