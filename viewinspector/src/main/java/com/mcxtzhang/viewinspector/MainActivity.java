package com.mcxtzhang.viewinspector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.viewinspector.suspend.DebugSuspendViewService;
import com.mcxtzhang.viewinspector.suspend.ServiceForegroundHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceForegroundHelper.startService(this, new Intent(this,
                DebugSuspendViewService.class));

    }

    static Activity sActivity;

    public static Activity getActivity() {
        return sActivity;
    }
}
