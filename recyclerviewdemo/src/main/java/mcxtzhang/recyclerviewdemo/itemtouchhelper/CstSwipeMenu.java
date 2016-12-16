package mcxtzhang.recyclerviewdemo.itemtouchhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/12/15.
 */

public class CstSwipeMenu extends SwipeMenuLayout {

    public CstSwipeMenu(Context context) {
        super(context);
    }

    public CstSwipeMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CstSwipeMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void smoothClose() {
        requestDisallowInterceptTouchEvent(true);
        super.smoothClose();
    }

    @Override
    public void smoothExpand() {
        requestDisallowInterceptTouchEvent(true);
        super.smoothExpand();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}
