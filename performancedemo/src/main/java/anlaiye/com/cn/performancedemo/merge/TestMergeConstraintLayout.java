package anlaiye.com.cn.performancedemo.merge;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import anlaiye.com.cn.performancedemo.R;

/**
 * Created by zhangxutong on 2018/4/23.
 */

public class TestMergeConstraintLayout extends ConstraintLayout {
    public TestMergeConstraintLayout(Context context) {
        super(context);
        init(context);
    }

    public TestMergeConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public TestMergeConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_test_merge_cl, this);
    }


}
