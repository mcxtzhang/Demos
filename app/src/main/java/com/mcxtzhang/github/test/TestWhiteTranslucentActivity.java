package com.mcxtzhang.github.test;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.github.R;

public class TestWhiteTranslucentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_white_translucent);

        findViewById(R.id.btnChangeAlpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.whiteView);
                Drawable background = view.getBackground()/*.mutate()*/;
                background.setAlpha(0);
/*                view.setBackground(background);
                view.invalidate();*/
                findViewById(R.id.root).invalidate();

            }
        });
    }
}
