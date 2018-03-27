package com.mcxtzhang.coordinatordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.coordinatordemo.alipay.AlipayMainActivity;
import com.mcxtzhang.coordinatordemo.ir.ImageRecognitionActivity;
import com.mcxtzhang.coordinatordemo.juejin.JuejinActivity;
import com.mcxtzhang.coordinatordemo.parallex.ParallexActivity;
import com.mcxtzhang.coordinatordemo.uc.UcActivity;
import com.mcxtzhang.coordinatordemo.zhihu.ZhihuActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.btnParallax).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ParallexActivity.class));
            }
        });
        findViewById(R.id.btnJuejin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, JuejinActivity.class));
            }
        });
        findViewById(R.id.btnZhihu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ZhihuActivity.class));
            }
        });
        findViewById(R.id.btnAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, AlipayMainActivity.class));
            }
        });
        findViewById(R.id.btnUc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, UcActivity.class));
            }
        });

        findViewById(R.id.btnIr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherActivity.this, ImageRecognitionActivity.class));
            }
        });
    }
}
