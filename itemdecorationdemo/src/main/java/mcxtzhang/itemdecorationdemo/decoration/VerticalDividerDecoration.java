package mcxtzhang.itemdecorationdemo.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalDividerDecoration extends RecyclerView.ItemDecoration {

    private LinearGradient mLinearGradient =
            new LinearGradient(0, 0, 0, 100, new int[] { Color.BLACK, 0 }, null,
                    Shader.TileMode.CLAMP);
    private Paint mPaint = new Paint();

    private Drawable mDivider;

    public VerticalDividerDecoration() {
        mDivider = new ColorDrawable(Color.BLACK);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + 25;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        mPaint.setShader(mLinearGradient);
        c.drawRect(0, 0, parent.getRight(), 100, mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        int itemPosition =
                ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        int totalItemCount = parent.getAdapter().getItemCount();
        if (itemPosition == totalItemCount - 1) {
            outRect.set(0, 0, 0, 0);
        } else {
/*            outRect.set(MainActivity.OUT_SET_LEFT, MainActivity.OUT_SET_TOP, MainActivity.OUT_SET_RIGHT,
                    MainActivity.OUT_SET_BOTTOM);*/
        }
    }
}