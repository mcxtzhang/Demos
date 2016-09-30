package mcxtzhang.recyclerviewdemo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍：自定义LayoutManager
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/09/28.
 */

public class CstLinearLayoutManager extends RecyclerView.LayoutManager {

    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    //记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
    private SparseBooleanArray hasAttachedItems = new SparseBooleanArray();


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //如果没有item，直接返回
        if (getItemCount() <= 0) {//参考源码
            removeAndRecycleAllViews(recycler);
            return;
        }

        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }

        detachAndScrapAttachedViews(recycler);

        //定义竖直方向的偏移量
        int offsetY = 0;
        totalHeight = 0;

        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);

            measureChildWithMargins(child, 0, 0);
            //把宽高拿到，宽高都是包含ItemDecorate的尺寸
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();

            //最后，将View布局
/*            layoutDecorated(child, getPaddingLeft() + lp.leftMargin, getPaddingTop() + lp.topMargin + offsetY,
                    getPaddingLeft() + lp.leftMargin + width + lp.rightMargin, getPaddingTop() + lp.topMargin + offsetY + height + lp.bottomMargin);*/


            Rect frame = allItemFrames.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(getPaddingLeft() + lp.leftMargin, getPaddingTop() + lp.topMargin + offsetY,
                    getPaddingLeft() + lp.leftMargin + width + lp.rightMargin, getPaddingTop() + lp.topMargin + offsetY + height + lp.bottomMargin);
            // 将当前的Item的Rect边界数据保存
            allItemFrames.put(i, frame);
            // 由于已经调用了detachAndScrapAttachedViews，因此需要将当前的Item设置为未出现过
            hasAttachedItems.put(i, false);

            //将竖直方向偏移量增大height
            offsetY += (height + lp.leftMargin + lp.bottomMargin);
        }

        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        totalHeight = Math.max(offsetY, getVerticalSpace());
        recycleAndFillItems(recycler, state);
    }

    /**
     * 回收不需要的Item，并且将需要显示的Item从缓存中取出
     */
    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }

        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(0, verticalScrollOffset, getHorizontalSpace(), verticalScrollOffset + getVerticalSpace());

        /**
         * 将滑出屏幕的Items回收到Recycle缓存中
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);
            //如果Item没有在显示区域，就说明需要回收
            if (!Rect.intersects(displayFrame, childFrame)) {
                //回收掉滑出屏幕的View
                removeAndRecycleView(child, recycler);

            }
        }

        //重新显示需要出现在屏幕的子View
        for (int i = 0; i < getItemCount(); i++) {

            if (Rect.intersects(displayFrame, allItemFrames.get(i))) {

                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = allItemFrames.get(i);
                //将这个item布局出来
                layoutDecorated(scrap,
                        frame.left,
                        frame.top - verticalScrollOffset,
                        frame.right,
                        frame.bottom - verticalScrollOffset);

            }
        }
    }


    //滑动
    @Override
    public boolean canScrollVertically() {

        return true;
    }

    private int verticalScrollOffset = 0;//滑动了多少距离
    private int totalHeight = 0;//view一共的高度


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //先detach掉所有的子View
        detachAndScrapAttachedViews(recycler);


        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 平移容器内的item 这句话实现移动
        offsetChildrenVertical(-travel);

        recycleAndFillItems(recycler, state);
        Log.d("zxt", " childView count:" + getChildCount());

        return travel;//返回0 没有fling效果
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingTop() - getPaddingBottom();
    }
}
