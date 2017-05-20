package com.mcxtzhang.databindingdemo.autobinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mcxtzhang.databindingdemo.R;

public class AutoBindingModifyValueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_binding_modify_value);
        ShopCartBean.getInstance().setNum((int) (Math.random() * -1000));
        ShopCartBean.getInstance().totalNum.set((int) (Math.random() * 1000));
    }
}
