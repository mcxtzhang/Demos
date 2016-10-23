package mcxtzhang.coordinatordemo;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/10/22.
 */

public class CstBehavior extends CoordinatorLayout.Behavior<Button> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        Log.e("TAG", "layoutDependsOn() called with: parent = [" + parent + "], child = [" + child + "], dependency = [" + dependency + "]");
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        Log.w("TAG", "onDependentViewChanged: ");
        child.setX(dependency.getWidth() / 2);
        child.setY(dependency.getTop() + 100);
        child.setText("哎呀 我呗关联了");
        return false;
    }
}
