package mcxtzhang.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/20.
 */

public class CstSysLM extends LinearLayoutManager {
    public CstSysLM(Context context) {
        super(context);
    }

    public CstSysLM(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CstSysLM(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int i = super.scrollVerticallyBy(dy, recycler, state);
        Log.d("TAG", "count= [" + getChildCount() + "]"+",[recycler.getScrapList().size():"+recycler.getScrapList().size());
        return i;
    }
}
