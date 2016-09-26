package com.mcxtzhang.databindingdemo;

import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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


        //发现嵌套的空指针也被自动判断了？
        TestBean2 testBean2 = new TestBean2("同名name");
        TestBean testBean = new TestBean(4, "测试。。。的空");
        //testBean.setName(null);
        testBean2.setTestBean(testBean);

        binding.setCstTestBean2(testBean2);
        //Toast.makeText(this, ""+testBean2.getTestBean().getName(), Toast.LENGTH_SHORT).show();


        //Observable Collections
        ObservableArrayMap<String, Object> testCollections = new ObservableArrayMap<>();
        testCollections.put("firstName", "Google");
        testCollections.put("lastName", "Inc.");
        testCollections.put("age", 17);
        binding.setVariable(BR.collectionMap, testCollections);
        mainPresenter.setTestCollectionMap(testCollections);

        setContentView(binding.getRoot());


        binding.setNestP(new NestBeanP());

    }

    public class NestBeanP {
        public void onNestBeanClick(TestBean2 testBean2){
            Toast.makeText(MainActivity.this, "嵌套的Bean，直接改变里面的Bean的内容，变不变", Toast.LENGTH_SHORT).show();
            //testBean2.getTestBean().setName("嵌套的Bean，直接改变里面的Bean的内容   我变了");
            testBean2.setTestBean(new TestBean(5,"直接set一个新Bean会改变？"));
        }
    }
}
