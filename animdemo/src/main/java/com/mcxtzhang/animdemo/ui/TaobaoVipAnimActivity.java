package com.mcxtzhang.animdemo.ui;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.mcxtzhang.animdemo.R;

public class TaobaoVipAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobao_vip_anim);

        final ProgressBar pb1 = (ProgressBar) findViewById(R.id.pb1);
        final ProgressBar pb2 = (ProgressBar) findViewById(R.id.pb2);

        pb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AnimatorSet set = new AnimatorSet();


                ValueAnimator anim1 = ValueAnimator.ofInt(0, 100);
                anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        pb1.setProgress((Integer) animation.getAnimatedValue());

                    }
                });

                ValueAnimator anim2 = ValueAnimator.ofInt(0, 100);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        pb2.setProgress((Integer) animation.getAnimatedValue());

                    }
                });
                set.playSequentially(anim1, anim2);
                set.setDuration(1000);
                set.start();
            }
        });
    }
}
