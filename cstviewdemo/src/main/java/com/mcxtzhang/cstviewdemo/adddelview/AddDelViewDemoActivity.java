package com.mcxtzhang.cstviewdemo.adddelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcxtzhang.cstviewdemo.R;
import com.mcxtzhang.cstviewdemo.adddelview.widget.AddDelView;
import com.mcxtzhang.cstviewdemo.adddelview.widget.IAddDelViewInterface;
import com.mcxtzhang.zxtcommonlib.recyclerview.CommonAdapter;
import com.mcxtzhang.zxtcommonlib.recyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AddDelViewDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_del_view_demo);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new CommonAdapter<AddDelBean>(this, R.layout.item_add_del, getDatas()) {
            @Override
            public void convert(ViewHolder holder, final AddDelBean addDelBean) {
                AddDelView addDelView = holder.getView(R.id.addDelView);
                addDelView.setCount(addDelBean.getCount());
                addDelView.setMaxCount(addDelBean.getMaxCount());
                addDelView.setOnAddDelListener(new IAddDelViewInterface.onAddDelListener() {
                    @Override
                    public void onAddSuccess(int count) {
                        addDelBean.setCount(count);
                    }

                    @Override
                    public void onAddFailed(int count, FailType failType) {

                    }

                    @Override
                    public void onDelSuccess(int count) {
                        addDelBean.setCount(count);
                    }

                    @Override
                    public void onDelFaild(int count, FailType failType) {

                    }
                });
/*                if (holder.getLayoutPosition() == 1) {
                    addDelView.setNoDelFunc(true);
                }else {
                    addDelView.setNoDelFunc(false);
                }*/
            }
        });
    }

    public List<AddDelBean> getDatas() {
        List<AddDelBean> result = new ArrayList<>();
        result.add(new AddDelBean(5, 1));
        result.add(new AddDelBean(10, 1));

        result.add(new AddDelBean(1, 1));
        result.add(new AddDelBean(3, 0));

        result.add(new AddDelBean(4, 2));

        return result;
    }
}
