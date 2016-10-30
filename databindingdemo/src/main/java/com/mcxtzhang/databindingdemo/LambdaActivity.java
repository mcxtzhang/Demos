package com.mcxtzhang.databindingdemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.databindingdemo.databinding.ActivityLambdaBinding;

public class LambdaActivity extends AppCompatActivity {
    private static final String TAG = "zxt/LambdaActivity";
    ActivityLambdaBinding mBinding;

    public class Presenter {


        public void onClick(TestBean testBean) {
            Log.d(TAG, "onClick() called with: testBean = [" + testBean + "]");
        }

        //longclick要特别注意，这里方法返回值是boolean
        public boolean onLongClick() {
            Log.d(TAG, "onLongClick() called");
            return false;
        }

        public void onFocusChange(TestBean testBean, Context context, boolean fcs, View view) {
            Log.d(TAG, "onFocusChange() called with: testBean = [" + testBean + "], context = [" + context + "], fcs = [" + fcs + "], view = [" + view + "]");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lambda);


        mBinding.setBean(new TestBean(2, "牛逼"));
        mBinding.setP(new Presenter());
    }
}
