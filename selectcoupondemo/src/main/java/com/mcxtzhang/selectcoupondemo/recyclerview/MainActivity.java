package com.mcxtzhang.selectcoupondemo.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcxtzhang.selectcoupondemo.R;
import com.mcxtzhang.selectcoupondemo.TestBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new CouponAdapter(initDatas(), this));
    }

    public List<TestBean> initDatas() {
        List<TestBean> datas = new ArrayList<>();
        datas.add(new TestBean("满100减99"));
        datas.add(new TestBean("满100减98", true));
        datas.add(new TestBean("满100减97"));
        datas.add(new TestBean("满100减96"));
        datas.add(new TestBean("满100减95"));
        datas.add(new TestBean("满100减94"));
        datas.add(new TestBean("满100减93"));
        datas.add(new TestBean("满100减92"));
        datas.add(new TestBean("满100减91"));
        datas.add(new TestBean("满100减90"));
        return datas;
    }
}
