package mcxtzhang.searchdemo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import mcxtzhang.searchdemo.abslistview.CommonAdapter;

public abstract class JokeFillterAdapter extends CommonAdapter<TestBean> implements Filterable {

    private List<TestBean> mCppyDatas;
    private static final String TAG = "TEST_SEARCH";
    private  MyFilter mFilter;

    public JokeFillterAdapter(Context context, int layoutId, List<TestBean> datas) {
        super(context, layoutId, datas);
        mCppyDatas = datas; // 备份数据
    }



    //当ListView调用setTextFilter()方法的时候，便会调用该方法
    @Override
    public Filter getFilter() {
        if (mFilter ==null){
            mFilter = new MyFilter();
        }
        return mFilter;
    }




    //我们需要定义一个过滤器的类来定义过滤规则

    /**
     * 自定义 Filter类
     *
     * 两个方法:
     * performFiltering 定义搜索规则。
     * publishResults 对搜索结果的处理
     */

    class MyFilter extends Filter{
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();

            List<TestBean> tempList ; // temp中间集合
            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
                tempList  = mCppyDatas;
            }else {//否则把符合条件的数据对象添加到集合中
                tempList = new ArrayList<>();
                for (TestBean recomend:mCppyDatas){
                    if (recomend.getName().contains(charSequence)||recomend.getUrl().contains(charSequence)){
                        Log.d(TAG,"performFiltering:"+recomend.toString());
                        tempList.add(recomend);
                    }

                }
            }
            result.values = tempList; //符合搜索的结果的数据集合
            result.count = tempList.size();//符合搜索的结果的数据个数

            return result;
        }
        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mDatas = (ArrayList<TestBean>)filterResults.values;
            Log.d(TAG,"publishResults:"+filterResults.count);
            if (filterResults.count>0){
                notifyDataSetChanged();//通知数据发生了改变
                Log.d(TAG,"publishResults:notifyDataSetChanged");
            }else {
                notifyDataSetInvalidated();//通知数据失效
                Log.d(TAG,"publishResults:notifyDataSetInvalidated");
            }
        }
    }




}