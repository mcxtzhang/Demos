package com.mcxtzhang.databindingdemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.mcxtzhang.databindingdemo.databinding.ActivityTwoBinding;
import com.mcxtzhang.databindingdemo.databinding.ItemFlowBinding;
import com.mcxtzhang.databindingdemo.flowgroup.FlowBean;

import java.util.ArrayList;
import java.util.List;

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





        FlowBean bean2 = new FlowBean("dd","");
        binding.setVisiData(bean2);

        //binding.setFlowDatas(initDatas());
        List<FlowBean> flowBeanList = initDatas();
        binding.flowLayout.removeAllViews();
        if (flowBeanList != null) {
            Context context = binding.flowLayout.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            for (final FlowBean bean : flowBeanList) {
                ItemFlowBinding itemFlowBinding = DataBindingUtil.inflate(inflater, R.layout.item_flow,  binding.flowLayout, false);
                itemFlowBinding.setData(bean);
                binding.flowLayout.addView(itemFlowBinding.getRoot());
            }
        }







    }

    public class NestBeanP {
        public void onNestBeanClick(TestBean2 testBean2){
            Toast.makeText(MainActivity.this, "嵌套的Bean，直接改变里面的Bean的内容，变不变", Toast.LENGTH_SHORT).show();
            //testBean2.getTestBean().setName("嵌套的Bean，直接改变里面的Bean的内容   我变了");
            testBean2.setTestBean(new TestBean(5,"直接set一个新Bean会改变？"));
        }
    }


    public List<FlowBean> initDatas() {
        List datas = new ArrayList<>();
        datas.add(new FlowBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
        datas.add(new FlowBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
        datas.add(new FlowBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
        datas.add(new FlowBean("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
        datas.add(new FlowBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
        datas.add(new FlowBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
        datas.add(new FlowBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
        datas.add(new FlowBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        return datas;
    }
}
