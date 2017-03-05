package com.mcxtzhang.daggerdemo.chap2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mcxtzhang.daggerdemo.R;

import javax.inject.Inject;

public class Main2Activity extends AppCompatActivity {

    TextView tvCoffee;
    @Inject
    Entity2 mEntity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();
    }

    private void initView() {
        tvCoffee = (TextView) findViewById(R.id.tv);
        tvCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCoffee();
            }
        });
    }

    private String makeCoffee() {
        return mEntity2.fukc();
    }

}
