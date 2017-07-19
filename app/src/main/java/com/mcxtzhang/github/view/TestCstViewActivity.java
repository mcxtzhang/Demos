package com.mcxtzhang.github.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ZRouter;
import com.mcxtzhang.github.R;

@ZRouter(path = "testView")
public class TestCstViewActivity extends AppCompatActivity {
    ViewGroup viewById;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cst_view);
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" + this + "]");
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" + getWindow() + "]");
        Log.d("TAG", "onCreate() called with: savedInstanceState = [" + getWindow().getWindowManager() + "]");


        /*findViewById(R.id.cstView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.invalidate();
                view.requestLayout();
            }
        });*/

        View view = new MyRelativeLayout(this);
        viewById= (ViewGroup) findViewById(R.id.root);
        viewById.addView(view);


        viewById.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestCstViewActivity.this, "ru如果我弹出，说明内存泄漏", Toast.LENGTH_SHORT).show();
            }
        },5000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewById.removeCallbacks(runnable);
        Toast.makeText(this, "destroy", Toast.LENGTH_SHORT).show();
    }
}
