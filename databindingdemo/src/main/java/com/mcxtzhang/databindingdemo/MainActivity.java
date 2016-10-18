package com.mcxtzhang.databindingdemo;

import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mcxtzhang.databindingdemo.databinding.ActivityTwoBinding;
import com.mcxtzhang.databindingdemo.flowgroup.FlowBean;
import com.mcxtzhang.zxtcommonlib.databinding.base.flow.FlowDatabindingAdapter;
import com.mcxtzhang.zxtcommonlib.widget.FlowLayout.FlowViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/


        final ActivityTwoBinding binding = ActivityTwoBinding.inflate(getLayoutInflater());
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


        FlowBean bean2 = new FlowBean("fgg", "");
        binding.setVisiData(bean2);

        //binding.setFlowDatas(initDatas());
        final List<FlowBean> flowBeanList = initDatas();
/*        binding.flowLayout.removeAllViews();
        if (flowBeanList != null) {
            Context context = binding.flowLayout.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            for (final FlowBean bean : flowBeanList) {
                ItemFlowBinding itemFlowBinding = DataBindingUtil.inflate(inflater, R.layout.item_flow, binding.flowLayout, false);
                itemFlowBinding.setData(bean);
                binding.flowLayout.addView(itemFlowBinding.getRoot());
            }
        }*/
        binding.flowLayout.setOnItemClickListener(new FlowViewGroup.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, FlowViewGroup parent) {
                Toast.makeText(MainActivity.this, "现在呢:" + flowBeanList.get(pos).getName(), Toast.LENGTH_SHORT).show();
                flowBeanList.add(new FlowBean("新增的", ""));
                binding.flowLayout.updateUI();
            }
        });

        //普通写法
/*        binding.flowLayout.setAdapter(new FlowSimpleAdapter<FlowBean>(flowBeanList, this, R.layout.item_flow) {
            @Override
            public void onBindView(ViewGroup parent, View itemView, FlowBean data, int pos) {
                TextView tv = (TextView) itemView.findViewById(R.id.tv);
                tv.setText(data.getName() + "new");
            }
        });*/

        //DataBinding的写法
        binding.flowLayout.setAdapter(new FlowDatabindingAdapter(flowBeanList, this, R.layout.item_flow));
/*        binding.flowLayout.setAdapter(new FlowBaseAdapter<FlowBean>(flowBeanList, this) {
            @Override
            public View onCreateView(ViewGroup parent, int pos) {
                ItemFlowBinding itemFlowBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_flow, binding.flowLayout, false);
                itemFlowBinding.setData(flowBeanList.get(pos));
                return itemFlowBinding.getRoot();
            }

            @Override
            public void onBindView(ViewGroup parent, View itemView, FlowBean data, int pos) {

            }
        });*/


    }

    public class NestBeanP {
        public void onNestBeanClick(TestBean2 testBean2) {
            Toast.makeText(MainActivity.this, "嵌套的Bean，直接改变里面的Bean的内容，变不变", Toast.LENGTH_SHORT).show();
            //testBean2.getTestBean().setName("嵌套的Bean，直接改变里面的Bean的内容   我变了");
            testBean2.setTestBean(new TestBean(5, "直接set一个新Bean会改变？"));
        }
    }


    public List<FlowBean> initDatas() {
        List datas = new ArrayList<>();
        datas.add(new FlowBean("1111", "张"));
        datas.add(new FlowBean("222", "旭童"));
        datas.add(new FlowBean("33333", "多种type"));
        datas.add(new FlowBean("4444444", ""));
        datas.add(new FlowBean("55555555", "多种type"));
        datas.add(new FlowBean("66666", "多种type"));
        datas.add(new FlowBean("7", "多种type"));
        datas.add(new FlowBean("88", "多种type"));
        return datas;
    }
}
