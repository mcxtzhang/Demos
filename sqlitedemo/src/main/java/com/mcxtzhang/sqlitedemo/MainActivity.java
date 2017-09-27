package com.mcxtzhang.sqlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mcxtzhang.sqlitedemo.save.OrderIdSaveFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et = (EditText) findViewById(R.id.et);

        findViewById(R.id.tvInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderIdSaveFactory.getOrderIdSaveManager(MainActivity.this).saveOrderId(et.getText().toString().trim());
            }
        });

        findViewById(R.id.tvQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = OrderIdSaveFactory.getOrderIdSaveManager(MainActivity.this).existOrderId(et.getText().toString().trim());
                Log.d("zxt", "onClick() called with: view = [" + b + "]");
            }
        });

    }
}
