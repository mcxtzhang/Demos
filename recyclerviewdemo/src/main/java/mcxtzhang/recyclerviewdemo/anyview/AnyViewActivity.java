package mcxtzhang.recyclerviewdemo.anyview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcxtzhang.commonadapter.databinding.rv.BaseBindingVH;
import com.mcxtzhang.commonadapter.databinding.rv.mul.BaseMulTypeBindingAdapter;
import com.mcxtzhang.commonadapter.databinding.rv.mul.IBaseMulInterface;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.recyclerviewdemo.R;
import mcxtzhang.recyclerviewdemo.anyview.bean.FragBean;
import mcxtzhang.recyclerviewdemo.anyview.bean.RvBean;
import mcxtzhang.recyclerviewdemo.anyview.dummy.DummyContent;
import mcxtzhang.recyclerviewdemo.databinding.ItemRvBinding;

public class AnyViewActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<IBaseMulInterface> datas = new ArrayList<>();
        datas.add(new FragBean());
        datas.add(new RvBean());
        recyclerView.setAdapter(new BaseMulTypeBindingAdapter(this, datas) {
                                    @Override
                                    public void onBindViewHolder(BaseBindingVH holder, int position) {
                                        super.onBindViewHolder(holder, position);
                                        switch (datas.get(position).getItemLayoutId()) {
                                            case R.layout.item_fragment:
                                                getSupportFragmentManager().beginTransaction().replace(R.id.flContainer,ItemFragment.newInstance(1)).commitAllowingStateLoss();
                                                break;
                                            case R.layout.item_rv:

                                                ItemRvBinding itemRvBinding = (ItemRvBinding) holder.getBinding();
                                                itemRvBinding.list.setLayoutManager(new LinearLayoutManager(mContext));
                                                itemRvBinding.list.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, AnyViewActivity.this));
                                                break;
                                        }

                                    }

                                }
        );


    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
