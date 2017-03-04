package com.mcxtzhang.aopdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.annotation.Pointcut;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zxt/AOP";

    public static String token = "a break originToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAspectJ(3, 5);
                Toast.makeText(MainActivity.this, MainActivity.token, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkAspectJ(int a, int b) {
        Log.d(TAG, String.valueOf(a + b));
    }

    //对应的PointCut代码，我要找到里面的checkAspectJ方法
    @Pointcut("execution(* *..checkAspectJ(..)) && args(a,b)")
    public void createPoint(int a, int b) {
        Log.d(TAG, "createPoint() called with: a = [" + a + "], b = [" + b + "]");
    }
}


