package com.mcxtzhang.databindingdemo.mullayout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.databindingdemo.BR;
import com.mcxtzhang.databindingdemo.R;
import com.mcxtzhang.databindingdemo.databinding.ActivityTestMulLayoutBinding;

/**
 * 测试多个include 的  Layout 会不会自动刷新数据
 */
public class TestMulLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MulLayoutBean1 mulLayoutBean1 = new MulLayoutBean1("张旭童", 20);

        MulLayoutBean3 mulLayoutBean3 = new MulLayoutBean3().setHint("bean3 总结：").setBean1(mulLayoutBean1);

        ActivityTestMulLayoutBinding layoutBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_test_mul_layout, null, false);
        layoutBinding.setVariable(BR.data1, mulLayoutBean1);
        layoutBinding.setVariable(BR.data3, mulLayoutBean3);
        setContentView(layoutBinding.getRoot());
    }
}
