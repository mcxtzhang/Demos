package anlaiye.com.cn.performancedemo.merge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import anlaiye.com.cn.performancedemo.R;

/**
 * Created by zhangxutong on 2018/4/23.
 */

public class TestMergeViewGroup extends RelativeLayout {
    public TestMergeViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TestMergeViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public TestMergeViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_test_merge_rl, this);
    }


}
