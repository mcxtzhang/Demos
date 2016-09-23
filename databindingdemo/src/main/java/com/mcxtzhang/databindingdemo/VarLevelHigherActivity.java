package com.mcxtzhang.databindingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.mcxtzhang.databindingdemo.databinding.ActivityVarLevelHigherBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class VarLevelHigherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVarLevelHigherBinding activityVarLevelHigherBinding = ActivityVarLevelHigherBinding.inflate(getLayoutInflater());
        ArrayList list = new ArrayList<String>();
        list.add("list第一个元素");
        activityVarLevelHigherBinding.setList(list);
        activityVarLevelHigherBinding.setListKey(0);

        HashMap<String,String> hashMap = new HashMap();
        hashMap.put("key1","第一个hashmap元素");
        activityVarLevelHigherBinding.setMap(hashMap);
        activityVarLevelHigherBinding.setMapKey("key1");


        ArrayList list2 = new ArrayList<String>();
        list2.add("list2 的的的第一个元素");
        activityVarLevelHigherBinding.setListOther(list2);

        //在代码里引用控件
        activityVarLevelHigherBinding.btn.setText("代码里操作她");

        setContentView(activityVarLevelHigherBinding.getRoot());

        //fvb的方式还是可以的吧？
        ((Button)findViewById(R.id.btn)).setText("我用fvb的方式再试试");
    }
}
