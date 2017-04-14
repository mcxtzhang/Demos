package com.mcxtzhang.github.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ZRouter;
import com.mcxtzhang.github.R;

@ZRouter(path = "testView")
public class TestCstViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cst_view);
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" + this + "]");
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" +getWindow() + "]");
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" +getWindow().getWindowManager() + "]");
    }
}
