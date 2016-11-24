package com.mcxtzhang.animdemo;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.PathInterpolator;

public class TestInterpolatorActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_interpolator);

        View viewById = findViewById(R.id.iv);

        PathInterpolator  pathInterpolator = new PathInterpolator(1.0f,1.0f);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewById,"translationX",0f,500f).setDuration(1000);
        objectAnimator.setInterpolator(pathInterpolator);


        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.start();
            }
        });

    }
}
