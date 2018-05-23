package com.mcxtzhang.constraintlayoutdemo.onedotone;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mcxtzhang.constraintlayoutdemo.R;

public class PercentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent);
        //anim
        final Button button = findViewById(R.id.button15);
        final ConstraintLayout viewGroup = (ConstraintLayout) findViewById(R.id.root);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anim1 直接用代码设置动画
                TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.root));
                //button.setMinHeight(600);
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) button.getLayoutParams();
                params.height = 400;
                params.topMargin = 100;
                button.setLayoutParams(params);

                findViewById(R.id.button22).setMinimumWidth(400);


                //anim2 用layout 自动生成动画
//                ConstraintSet constraintSet = new ConstraintSet();
//                constraintSet.clone(PercentActivity.this, R.layout.activity_percent_animed);
//
//                //设置差值加速器
//
//                ChangeBounds changeBounds = new ChangeBounds();
//                changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
//
//                TransitionManager.beginDelayedTransition(viewGroup,changeBounds);
//                constraintSet.applyTo(viewGroup);


            }
        });
    }
}
