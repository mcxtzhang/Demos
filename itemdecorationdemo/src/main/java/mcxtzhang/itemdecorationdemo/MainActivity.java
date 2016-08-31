package mcxtzhang.itemdecorationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.itemdecorationdemo.decoration.DividerItemDecoration;
import mcxtzhang.itemdecorationdemo.decoration.TitleItemDecoration;

public class MainActivity extends Activity {
    private static final String TAG = "zxt";
    private RecyclerView mRv;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas;

    private TitleItemDecoration mDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        initDatas();
        mRv.setAdapter(mAdapter = new CityAdapter(this, mDatas));
        mRv.addItemDecoration(mDecoration = new TitleItemDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        mDatas.add(new CityBean("A", "安徽"));
        mDatas.add(new CityBean("B", "北京"));
        mDatas.add(new CityBean("F", "福建"));
        mDatas.add(new CityBean("G", "广东"));
        mDatas.add(new CityBean("G", "甘肃"));
        mDatas.add(new CityBean("G", "贵州"));
        mDatas.add(new CityBean("G", "广西"));
        mDatas.add(new CityBean("H", "河南"));
        mDatas.add(new CityBean("H", "湖北"));
        mDatas.add(new CityBean("H", "湖南"));
        mDatas.add(new CityBean("H", "河北"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("J", "江苏"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));
        mDatas.add(new CityBean("R", "日本"));

    }
}
