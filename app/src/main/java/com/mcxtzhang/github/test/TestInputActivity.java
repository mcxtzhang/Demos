package com.mcxtzhang.github.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.github.R;

public class TestInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_input);
        ViewGroup vg = (ViewGroup) findViewById(R.id.root);
        int flag = getWindow().getDecorView().getSystemUiVisibility();
        flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE ;
        getWindow().getDecorView().setSystemUiVisibility(flag);

    }
}
