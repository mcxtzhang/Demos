package com.mcxtzhang.animdemo.text;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mcxtzhang.animdemo.R;

public class TextViewDianDianDIanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_dian_dian_dian);
        final TextView tv = (TextView) findViewById(R.id.textView);


        final String hint3 = "发现一张美图，努力计算中...";
        final String hint2 = hint3.substring(0, hint3.length() - 1);
        final String hint1 = hint3.substring(0, hint3.length() - 2);



        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 3);
        valueAnimator.setDuration(1500)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        if (value < 1) {
                            tv.setText(hint1);
                        } else if (value < 2) {
                            tv.setText(hint2);
                        } else {
                            tv.setText(hint3);
                        }
                    }
                });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tv.setText(hint1);
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });


    }
}
