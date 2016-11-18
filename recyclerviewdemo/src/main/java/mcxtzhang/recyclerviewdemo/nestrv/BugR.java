package mcxtzhang.recyclerviewdemo.nestrv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/8.
 */

public class BugR extends RecyclerView {
    public BugR(Context context) {
        super(context);
    }

    public BugR(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BugR(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        //super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        //super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        //super.onVisibilityAggregated(isVisible);
    }
}
