package com.mcxtzhang.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Toolbar mToolbar = null;
    protected TextView mToolbarTitleTextView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id toolbar");
        }
        setSupportActionBar(mToolbar);
        mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbarTitleTextView != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!isChild()) {
            onTitleChanged(getTitle(), getTitleColor());
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTitleTextView != null) {
            Log.d("TAG", "onTitleChanged() called with: title = [" + title + "], mToolbarTitleTextView = [" + mToolbarTitleTextView.getWidth() + "]");
            mToolbarTitleTextView.setText(title);
            mToolbarTitleTextView.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "onTitleChanged() called with: title = ["  + "], mToolbarTitleTextView = [" + mToolbarTitleTextView.getWidth() + "]");
                }
            });
        }
    }


}
