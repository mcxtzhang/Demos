package com.mcxtzhang.databindingdemo;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.mcxtzhang.databindingdemo.databinding.ActivityTwoBinding;

public class MainActivity extends AppCompatActivity {

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/


        ActivityTwoBinding binding = ActivityTwoBinding.inflate(getLayoutInflater());
        mainPresenter = new MainPresenter(this);
        binding.setMainPresenter(mainPresenter);
        //activityTwoBinding.setTestBean(new TestBean(1,"张旭童"));
        //或者直接通过 setVariable (查看生成的代码，这种方式多了一个switch)
        binding.setVariable(BR.cstTestBean, new TestBean(2, "另一种方式设置 setVariable"));

        binding.setCstTestBean2(new TestBean2("同名name"));


        //Observable Collections
        ObservableArrayMap<String,Object> testCollections = new ObservableArrayMap<>();
        testCollections.put("firstName", "Google");
        testCollections.put("lastName", "Inc.");
        testCollections.put("age", 17);
        binding.setVariable(BR.collectionMap,testCollections);
        mainPresenter.setTestCollectionMap(testCollections);

        setContentView(binding.getRoot());

    }
}
