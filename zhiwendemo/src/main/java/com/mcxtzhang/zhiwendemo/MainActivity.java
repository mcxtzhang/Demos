package com.mcxtzhang.zhiwendemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.mcxtzhang.zhiwendemo.FingerUtils.isOpenFingerDetect;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt/finger";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Toast.makeText(MainActivity.this, "不支持指纹之别", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isOpenFingerDetect(MainActivity.this, ((EditText) findViewById(R.id.editText)).getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "跳转指纹", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SecretActivity.class));
/*                    FingerDialogFragment fingerDialogFragment = new FingerDialogFragment();
                    fingerDialogFragment.show(getFragmentManager(), "dialog");*/
                } else {
                    Toast.makeText(MainActivity.this, "不跳指纹之别", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SecretActivity.class));
                }
            }
        });
    }


}
