package com.mcxtzhang.cstviewdemo;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SvgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        final ImageView mImgCheck = (ImageView) findViewById(R.id.imageView);
        mImgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable) mImgCheck.getDrawable()).start();
            }
        });

    }
}
