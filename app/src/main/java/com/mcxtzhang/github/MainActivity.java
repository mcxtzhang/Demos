package com.mcxtzhang.github;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.mcxtzhang.github.bean.EnumInfoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "zxt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnHAHA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
                findViewById(R.id.btnHAHA).getLocationInWindow(location);
                Log.d(TAG, "onCreate() called with: location = [" + location[0] + "]"+ location[1] + "]");

                int[] location2 = new int[2];
                findViewById(R.id.btnHAHA).getLocationOnScreen(location2);
                Log.d(TAG, "onCreate() called with: location = [" + location2[0] + "]"+ location2[1] + "]");


                int[] location3 = new int[2];
                v.getLocationInWindow(location3);
                Log.d(TAG, "onCreate() called with: location = [" + location3[0] + "]"+ location3[1] + "]");

                int[] location4 = new int[2];
                v.getLocationOnScreen(location4);
                Log.d(TAG, "onCreate() called with: location = [" + location4[0] + "]"+ location4[1] + "]");
            }
        });










        List<EnumInfoBean> enumInfoBeen1 = EnumInfoBean.fakerDatas();

        for (EnumInfoBean bean : enumInfoBeen1) {

            bean.setSelected(true);

        }


        List<EnumInfoBean> enumInfoBeen2 = new ArrayList<>();

        for (EnumInfoBean bean : enumInfoBeen1) {

            enumInfoBeen2.add((EnumInfoBean) bean.clone());

        }



    }
}
