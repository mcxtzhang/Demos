package anlaiye.com.cn.artdemo.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import anlaiye.com.cn.artdemo.R;

public class DActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        Log.e(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //A-B-C-D，依次启动，从D利用clearTop 回到A，会先执行 B C 的onDestroy()，然后D的onPause() ->A 会先销毁自己，onDestroy()->onCreate()-onStart().... 所以不会出现闪屏。但是A会销毁自己一次
                //如果A是singleTop 则不会销毁 跳过onDestroy-onCreate,回回调onNewIntent
                Intent intent = new Intent(DActivity.this, AActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private static final String TAG = "zxt/DActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
    }
}
