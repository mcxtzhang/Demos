package mcxtzhang.recyclerviewdemo.generalizerv;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.mcxtzhang.commonadapter.databinding.rv.BaseBindingAdapter;
import com.mcxtzhang.commonadapter.rv.HeaderRecyclerAndFooterWrapperAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcxtzhang.recyclerviewdemo.R;
import mcxtzhang.recyclerviewdemo.TestBean;
import mcxtzhang.recyclerviewdemo.databinding.ActivityBaseRecyclerViewBinding;
import mcxtzhang.recyclerviewdemo.databinding.ItemBaseIv1Binding;
import mcxtzhang.recyclerviewdemo.generalizerv.base.ZRecyclerView;
import mcxtzhang.recyclerviewdemo.generalizerv.base.widget.LoadingFooter;

public class BaseRecyclerViewActivity extends AppCompatActivity {
    ActivityBaseRecyclerViewBinding mBinding;

    BaseBindingAdapter<TestBean, ItemBaseIv1Binding> mInnerAdapter;
    HeaderRecyclerAndFooterWrapperAdapter mWrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_recycler_view);
        mBinding.setP(new Presenter());

        mInnerAdapter = new BaseBindingAdapter<>(this, initDatas(), R.layout.item_base_iv_1);
        mBinding.rv.setAdapter(mWrapperAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mInnerAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {

            }
        });
        mBinding.rv.setOnLoadMoreListener(new ZRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mBinding.rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random(System.nanoTime());
                        boolean success = random.nextBoolean();
                        if (success) {
                            boolean isEmpty = random.nextBoolean();
                            if (isEmpty) {
                                mBinding.rv.setFooterState(LoadingFooter.State.End);
                            } else {
                                mInnerAdapter.addDatas(initDatas());
                                mWrapperAdapter.notifyDataSetChanged();
                                mBinding.rv.setFooterState(LoadingFooter.State.Normal);
                            }

                        } else {
                            mBinding.rv.setFooterState(LoadingFooter.State.Error);
                        }

                    }
                }, 2000);
            }
        });

    }

    public List<TestBean> initDatas() {
        List<TestBean> datas = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < 1; i++) {
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", j++ + "张"));
            datas.add(new TestBean("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", j++ + "旭童"));
            datas.add(new TestBean("http://news.k618.cn/tech/201604/W020160407281077548026.jpg", j++ + "多种type"));
            datas.add(new TestBean("http://www.kejik.com/image/1460343965520.jpg", j++ + "多种type"));
            datas.add(new TestBean("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", j++ + "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", j++ + "多种type"));
            datas.add(new TestBean("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", j++ + "多种type"));
            datas.add(new TestBean("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", j++ + "多种type"));
        }
        return datas;
    }

    public class Presenter {
        public void onLinearClick(View view) {
            mBinding.rv.setLayoutManager(new LinearLayoutManager(BaseRecyclerViewActivity.this, LinearLayoutManager.VERTICAL, false));
        }

        public void onGridClick(View view) {
            mBinding.rv.setLayoutManager(new GridLayoutManager(BaseRecyclerViewActivity.this, 3, LinearLayoutManager.VERTICAL, false));
        }

        public void onStaggeredClick(View view) {
            mBinding.rv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        }
    }

}
