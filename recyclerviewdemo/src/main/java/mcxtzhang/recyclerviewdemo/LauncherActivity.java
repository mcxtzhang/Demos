package mcxtzhang.recyclerviewdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mcxtzhang.recyclerviewdemo.anyview.AnyViewActivity;
import mcxtzhang.recyclerviewdemo.databinding.ActivityLauncherBinding;
import mcxtzhang.recyclerviewdemo.expand.ExpandRvActivity;
import mcxtzhang.recyclerviewdemo.generalizerv.BaseRecyclerViewActivity;
import mcxtzhang.recyclerviewdemo.itemtouchhelper.TestItemHelperActivity;
import mcxtzhang.recyclerviewdemo.nestrv.ViewPagerNestRvActivity;

public class LauncherActivity extends AppCompatActivity {
    ActivityLauncherBinding mBinding;

    public class Presenter {
        public void onGeneralizeRv(View view) {
            startActivity(new Intent(LauncherActivity.this, BaseRecyclerViewActivity.class));
        }

        public void onCstLmRv(View view) {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        }

        public void onVpNestRv(View view) {
            startActivity(new Intent(LauncherActivity.this, ViewPagerNestRvActivity.class));
        }

        public void onAnyViewRv(View view) {
            startActivity(new Intent(LauncherActivity.this, AnyViewActivity.class));
        }

        public void onItemTouchHelperClick(View view) {
            startActivity(new Intent(LauncherActivity.this, TestItemHelperActivity.class));
        }

        public void onNestExpandClick(View v) {
            startActivity(new Intent(LauncherActivity.this, ExpandRvActivity.class));
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);
        mBinding.setP(new Presenter());
    }
}
