package com.mcxtzhang.github.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mcxtzhang.github.R;

public class TestInputActivity extends AppCompatActivity {
    boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_input);
        /*ViewGroup vg = (ViewGroup) findViewById(R.id.root);
        int flag = getWindow().getDecorView().getSystemUiVisibility();
        flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
        getWindow().getDecorView().setSystemUiVisibility(flag);*/
        findViewById(R.id.ivPhoto).setVisibility(View.INVISIBLE);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible) {
                    visible = false;
                    findViewById(R.id.ivPhoto).setAlpha(0);
                } else {
                    visible = true;
                    //findViewById(R.id.ivPhoto).setVisibility(View.VISIBLE);
                    findViewById(R.id.ivPhoto).setAlpha(255);
                }

                Toast.makeText(TestInputActivity.this, "visible:" + visible, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
