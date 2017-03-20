package com.mcxtzhang.github.touch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ZRouter;
import com.mcxtzhang.github.R;

@ZRouter(path = "touch")
public class TouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }
}
