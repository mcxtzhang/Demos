package com.mcxtzhang.databindingdemo.autobinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.databindingdemo.databinding.ActivityMaiBinding;

public class AutoBindingActivity extends AppCompatActivity {
    //private static AutoBindingActivity instance;
    ActivityMaiBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instance = this;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_mai, null, false);
        setContentView(mBinding.getRoot());

        ShopCartBean shopCartBean = ShopCartBean.getInstance();
        shopCartBean.setNum(6);
        mBinding.setShopCartBean(shopCartBean);
        shopCartBean.totalNum.set(10);

        mBinding.btnAddShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShopCartBean.getInstance().setNum((int) (Math.random()*1000));
                startActivity(new Intent(AutoBindingActivity.this, AutoBindingModifyValueActivity.class));
            }
        });
    }
}
