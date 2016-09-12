package com.mcxtzhang.diffutilsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TestBean> mDatas;
    private RecyclerView mRv;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        //mAdapter.setData(mDatas);
        mRv.setAdapter(mAdapter);
    }

    public void change(View view) {
        ArrayList<TestBean> newDatas = new ArrayList<>(mDatas);
        //newDatas.add(new TestBean("赵子龙","帅"));
        newDatas.get(0).setDesc("Android+");
        TestBean testBean = newDatas.get(1);
        newDatas.remove(testBean);
        newDatas.add(testBean);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
        diffResult.dispatchUpdatesTo(mAdapter);
        //别忘了将新数据给Adapter
        mDatas = newDatas;
        mAdapter.setDatas(mDatas);


/*        mDatas.set(1, new TestBean("Fndroid", "2"));
        mDatas.add(new TestBean("Jason", "8"));
        TestBean s2 = mDatas.get(2);
        mDatas.remove(2);
        mDatas.add(s2);

        ArrayList<TestBean> old_testBeen = mAdapter.getData();
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyCallback(old_testBeen, mDatas), true);
        mAdapter.setData(mDatas);
        result.dispatchUpdatesTo(mAdapter);*/
    }

/*    private class MyCallback extends DiffUtil.Callback {
        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }

        private ArrayList<TestBean> old_testBeen, new_testBeen;

        MyCallback(ArrayList<TestBean> data, ArrayList<TestBean> testBeen) {
            this.old_testBeen = data;
            this.new_testBeen = testBeen;
        }

        @Override
        public int getOldListSize() {
            return old_testBeen.size();
        }

        @Override
        public int getNewListSize() {
            return new_testBeen.size();
        }

        // 判断Item是否已经存在
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return old_testBeen.get(oldItemPosition).getNum() == new_testBeen.get(newItemPosition).getNum();
        }

        // 如果Item已经存在则会调用此方法，判断Item的内容是否一致
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return old_testBeen.get(oldItemPosition).getName().equals(new_testBeen.get(newItemPosition).getName());
        }
    }*/


    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("张旭童1", "Android"));
        mDatas.add(new TestBean("张旭童2", "Java"));
        mDatas.add(new TestBean("张旭童3", "背锅"));
        mDatas.add(new TestBean("张旭童4", "手撕产品"));
        mDatas.add(new TestBean("张旭童5", "手撕测试"));
    }


/*    class MyAdapter extends RecyclerView.Adapter {
        private ArrayList<TestBean> data;

        ArrayList<TestBean> getData() {
            return data;
        }

        void setData(List<TestBean> data) {
            this.data = new ArrayList<>(data);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.itemview, null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            TestBean testBean = data.get(position);
            myViewHolder.tv.setText(testBean.getNum() + "." + testBean.getName());
            myViewHolder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "onClick() called with: position = [" + position + "]");
                    Log.d("TAG", "onClick() called with: position = [" + myViewHolder.getAdapterPosition() + "]");
                    Log.d("TAG", "onClick() called with: position = [" + myViewHolder.getLayoutPosition() + "]");
                }
            });
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_tv);
            }
        }
    }*/
}
