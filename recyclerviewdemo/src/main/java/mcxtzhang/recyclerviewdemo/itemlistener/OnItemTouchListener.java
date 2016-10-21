package mcxtzhang.recyclerviewdemo.itemlistener;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 介绍：还没能解决 touch事件和内部View事件冲突的问题
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/10/21.
 */

public abstract class OnItemTouchListener<T extends RecyclerView.ViewHolder, D> implements RecyclerView.OnItemTouchListener {
    protected abstract void onItemClick(T viewHolder, D data, View itemView, int position);

    protected abstract void onItemLongClick(T viewHolder, D data, View itemView, int position);

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView mRv;
    private List<D> mDatas;

    public OnItemTouchListener(Context context, RecyclerView rv, List<D> datas) {
        mRv = rv;
        mDatas = datas;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = mRv.findChildViewUnder(e.getX(), e.getY());
                if (null != view) {
                    if (view instanceof ViewGroup) {
                        ViewGroup vg = (ViewGroup) view;
                        Rect bounds = null;
                        for (int i = 0; i < vg.getChildCount(); i++) {
                            View child = vg.getChildAt(i);
                            bounds = new Rect();
                            child.getLocalVisibleRect(bounds);
                            Log.d("TAG1", "onSingleTapUp() called with: bounds = [" + bounds + "]");
                        }

                    }
                    int position = mRv.getChildLayoutPosition(view);
                    onItemClick((T) mRv.findContainingViewHolder(view), mDatas.get(position), view, position);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View view = mRv.findChildViewUnder(e.getX(), e.getY());
                if (null != view) {
                    int position = mRv.getChildLayoutPosition(view);
                    onItemLongClick((T) mRv.findContainingViewHolder(view), mDatas.get(position), view, position);
                }
            }
        });
    }

    public View findChildViewUnder(ViewGroup viewGroup, float x, float y) {
        final int count = viewGroup.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = viewGroup.getChildAt(i);
            final float translationX = ViewCompat.getTranslationX(child);
            final float translationY = ViewCompat.getTranslationY(child);
            if (x >= child.getLeft() + translationX &&
                    x <= child.getRight() + translationX &&
                    y >= child.getTop() + translationY &&
                    y <= child.getBottom() + translationY) {
                return child;
            }
        }
        return null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
