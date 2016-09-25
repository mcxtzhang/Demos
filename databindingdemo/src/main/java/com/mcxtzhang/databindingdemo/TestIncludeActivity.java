package com.mcxtzhang.databindingdemo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.mcxtzhang.databindingdemo.databinding.ActivityTestIncludeBinding;

public class TestIncludeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTestIncludeBinding binding = ActivityTestIncludeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.setCstOutTestBean(new TestBean(3,"实验include"));

    }


/*    private ViewStub.OnInflateListener mProxyListener = new ViewStub.OnInflateListener() {
        @Override
        public void onInflate(ViewStub stub, View inflated) {
            mRoot = inflated;
            mViewDataBinding = DataBindingUtil.bind(mContainingBinding.mBindingComponent,
                    inflated, stub.getLayoutResource());
            mViewStub = null;

            if (mOnInflateListener != null) {
                mOnInflateListener.onInflate(stub, inflated);
                mOnInflateListener = null;
            }
            mContainingBinding.invalidateAll();
            mContainingBinding.forceExecuteBindings();
        }
    };*/
}
