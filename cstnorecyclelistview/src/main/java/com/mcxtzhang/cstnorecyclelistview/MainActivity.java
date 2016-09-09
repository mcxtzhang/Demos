package com.mcxtzhang.cstnorecyclelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas;
    private CstFullShowListView cstFullShowListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        cstFullShowListView = (CstFullShowListView) findViewById(R.id.cstFullShowListView);

        cstFullShowListView.setAdapter(new FullListViewAdapter<TestBean>(R.layout.item_lv, mDatas
        ) {
            @Override
            void onBind(int pos, TestBean testBean, View v) {
                TextView tv = (TextView) v.findViewById(R.id.tv);
                tv.setText(testBean.getName());
            }
        });


    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mDatas.add(new TestBean(i + ""));
        }
    }

    public void add(View view) {
        mDatas.add(new TestBean("add"));
        cstFullShowListView.updateUI();
    }

    public void del(View view) {
        mDatas.remove(mDatas.size() - 1);
        cstFullShowListView.updateUI();
    }
}
