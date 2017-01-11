package com.mcxtzhang.dbflowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstModel firstModel = new FirstModel();
                firstModel.setName("张旭童");
                firstModel.save();


            }
        });


        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstModel firstModel = new Select().from(FirstModel.class).querySingle();
                if (null != firstModel) {
                    Toast.makeText(MainActivity.this, "" + firstModel, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "not find First Model", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
