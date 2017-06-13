package com.mcxtzhang.zhiwendemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static com.mcxtzhang.zhiwendemo.FingerUtils.createKeyPair;
import static com.mcxtzhang.zhiwendemo.FingerUtils.isOpenFingerDetect;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/finger";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Toast.makeText(MainActivity.this, "不支持指纹之别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isOpenFingerDetect(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "支持1", Toast.LENGTH_SHORT).show();
                    createKeyPair();
                    startActivity(new Intent(MainActivity.this,FingerDetectActivity.class));

                } else {
                    Toast.makeText(MainActivity.this, "不支持指纹之别", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





}
