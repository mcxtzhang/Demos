package com.mcxtzhang.toastdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WestToast.create(this);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WestToast.show("化解啊哈");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WestToast.destroy();
    }
}
