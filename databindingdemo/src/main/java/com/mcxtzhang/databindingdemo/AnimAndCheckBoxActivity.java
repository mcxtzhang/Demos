package com.mcxtzhang.databindingdemo;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.databindingdemo.databinding.ActivityAnimBinding;

public class AnimAndCheckBoxActivity extends AppCompatActivity {
    private static final String TAG = "zxt/AnimAndCheckBoxActivity";
    ActivityAnimBinding mBinding;

    public class Presenter {
        public void onCheckedChangeListener(View view, boolean isChecked) {
            Log.d(TAG, "onCheckedChangeListener() called with: view = [" + view + "], isChecked = [" + isChecked + "]");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_anim);
        mBinding.setP(new Presenter());

        mBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return super.onPreBind(binding);
            }
        });
    }
}
