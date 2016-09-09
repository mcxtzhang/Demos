package com.mcxtzhang.cstnorecyclelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        CstFullShowListView cstFullShowListView = (CstFullShowListView) findViewById(R.id.cstFullShowListView);

        cstFullShowListView.setItemLayoutId(R.layout.item_lv).setOnBindListener(new CstFullShowListView.onBindListener<TestBean>() {
            @Override
            public void onBind(int pos, TestBean testBean, View v) {
                TextView tv = (TextView) v.findViewById(R.id.tv);
                tv.setText(testBean.getName());
                if (pos%2==0){
                    v.setVisibility(View.GONE);
                }
            }
        }).setmDatas(mDatas);

    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDatas.add(new TestBean(i + ""));
        }
    }
}
