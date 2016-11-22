package com.mcxtzhang.cstviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mcxtzhang.cstviewdemo.widget.adddelview.AddDelView;
import com.mcxtzhang.cstviewdemo.widget.adddelview.IAddDelViewInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddDelView addDelView = (AddDelView) findViewById(R.id.addDelView);
        addDelView.setOnAddDelListener(new IAddDelViewInterface.onAddDelListener() {
            @Override
            public void onAddSuccess(int count) {

            }

            @Override
            public void onAddFailed(int count, FailType failType) {
                Toast.makeText(MainActivity.this, "add faild:" + count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelSuccess(int count) {

            }

            @Override
            public void onDelFaild(int count, FailType failType) {
                Toast.makeText(MainActivity.this, "onDelFaild faild:" + count, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
