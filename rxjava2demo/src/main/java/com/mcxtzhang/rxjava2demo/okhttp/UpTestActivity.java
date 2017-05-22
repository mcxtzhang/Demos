package com.mcxtzhang.rxjava2demo.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.rxjava2demo.R;

public class UpTestActivity extends AppCompatActivity {
    public static String url = "http://imgsvc.imcoming.com.cn/img/upload/4tpBNVAu7iPQgmQetUXvXA/multifile/multipart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_test);

        findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add by zhangxutong change to okhttp


            }
        });


    }
}
