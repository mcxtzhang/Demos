package com.mcxtzhang.learnannotationdemo.expand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mcxtzhang.learnannotationdemo.R;
import com.mcxtzhang.learnannotationdemo.expand.helper.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas = new ArrayList<TestBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        mTree = (ListView) findViewById(R.id.lv);
        try {
            mAdapter = new SimpleTreeAdapter<TestBean>(mTree, this, mDatas, 1);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initDatas() {

        // id , pid , label , 其他属性
        mDatas.add(new TestBean(1, 0, "文件管理系统"));
        mDatas.add(new TestBean(2, 1, "游戏"));
        mDatas.add(new TestBean(3, 1, "文档"));
        mDatas.add(new TestBean(4, 1, "程序"));
        mDatas.add(new TestBean(5, 2, "war3"));
        mDatas.add(new TestBean(6, 2, "刀塔传奇"));

        mDatas.add(new TestBean(7, 4, "面向对象"));
        mDatas.add(new TestBean(8, 4, "非面向对象"));

        mDatas.add(new TestBean(9, 7, "C++"));
        mDatas.add(new TestBean(10, 7, "JAVA"));
        mDatas.add(new TestBean(11, 7, "Javascript"));
        mDatas.add(new TestBean(12, 8, "C"));

    }
}
