package com.mcxtzhang.selectcoupondemo.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.mcxtzhang.selectcoupondemo.R;
import com.mcxtzhang.selectcoupondemo.TestBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;

    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = (RecyclerView) findViewById(R.id.rv);
        //mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setLayoutManager(mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRv.setAdapter(new CouponAdapter(initDatas(), this, mRv));


        mRv.post(new Runnable() {
            @Override
            public void run() {
                int[] firstVisibleItemPositions = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null);
                int[] lastVisibleItemPositions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
                Log.d("zxt", "onCreate() called with: firstVisibleItemPositions = [" + firstVisibleItemPositions[0] + "]");

                Log.d("zxt", "onCreate() called with: lastVisibleItemPositions = [" + lastVisibleItemPositions[lastVisibleItemPositions.length - 1] + "]");
            }
        });
    }

    public List<TestBean> initDatas() {
        List<TestBean> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new TestBean("满100减99"));
            datas.add(new TestBean("满100减98", i == 0 ? true : false));
            datas.add(new TestBean("满100减97"));
            datas.add(new TestBean("满100减96"));
            datas.add(new TestBean("满100减95"));
            datas.add(new TestBean("满100减94"));
            datas.add(new TestBean("满100减93"));
            datas.add(new TestBean("满100减92"));
            datas.add(new TestBean("满100减91"));
            datas.add(new TestBean("满100减90"));
        }
        return datas;
    }
}
