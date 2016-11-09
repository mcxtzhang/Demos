package mcxtzhang.recyclerviewdemo.nestrv;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.recyclerviewdemo.R;
import mcxtzhang.recyclerviewdemo.databinding.ActivityViewPagerNestRvBinding;

public class ViewPagerNestRvActivity extends AppCompatActivity {
    ActivityViewPagerNestRvBinding mBinding;

    List<NestRvFragment1 > mFragmentDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_pager_nest_rv);


        mFragmentDatas = new ArrayList<>();

        mFragmentDatas.add(new NestRvFragment1());
        mFragmentDatas.add(new NestRvFragment1());

        mBinding.vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentDatas.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentDatas.size();
            }
        });


    }
}
