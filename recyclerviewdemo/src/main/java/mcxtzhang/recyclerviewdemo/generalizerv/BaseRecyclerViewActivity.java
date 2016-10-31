package mcxtzhang.recyclerviewdemo.generalizerv;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.mcxtzhang.zxtcommonlib.databinding.base.BaseBindingAdapter;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.recyclerviewdemo.R;
import mcxtzhang.recyclerviewdemo.TestBean;
import mcxtzhang.recyclerviewdemo.databinding.ActivityBaseRecyclerViewBinding;
import mcxtzhang.recyclerviewdemo.databinding.ItemBaseIv1Binding;

public class BaseRecyclerViewActivity extends AppCompatActivity {
    ActivityBaseRecyclerViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_recycler_view);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rv.setAdapter(new BaseBindingAdapter<TestBean, ItemBaseIv1Binding>(this, R.layout.item_base_iv_1, initDatas()));
    }

    public List<TestBean> initDatas() {
        List<TestBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张"));
            datas.add(new TestBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童"));
            datas.add(new TestBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type"));
            datas.add(new TestBean("http://www.kejik.com/image/1460343965520.jpg", "多种type"));
            datas.add(new TestBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type"));
            datas.add(new TestBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type"));
        }
        return datas;
    }

}
