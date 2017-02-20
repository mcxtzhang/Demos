package anlaiye.com.cn.artdemo.c1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import anlaiye.com.cn.artdemo.R;

public class LifeCycleTest2Activity extends Activity {
    private static final String TAG = "zxt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_test2);
        Log.w(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        findViewById(R.id.btnReStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LifeCycleTest2Activity.this, LifeCycleTest2Activity.class));
                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse("file://abc"),"image/png");
            }
        });

        findViewById(R.id.btnJump1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LifeCycleTest2Activity.this, LifeCycleTestActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "onRestart() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.w(TAG, "onSaveInstanceState() called with: outState = [" + outState + "], outPersistentState = [" + outPersistentState + "]");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(TAG, "onRestoreInstanceState() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy() called");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent() called with: intent = [" + intent + "]");
    }
}
