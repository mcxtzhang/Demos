package com.mcxtzhang.viewpagerdemo.optimise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mcxtzhang.viewpagerdemo.R;

public class ImageTestActivity extends AppCompatActivity {

    protected Button mButton;
    protected ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.image);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });

        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearImage();
                return true;
            }
        });


        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.postDelayed(autoCleanRunable, 5000);
                startActivity(new Intent(ImageTestActivity.this, EmptyActivity.class));
            }
        });

    }

    Bitmap bitmap;

    protected void clearImage() {
        bitmap = null;
        mImageView.setImageBitmap(bitmap);
    }

    protected void showImage() {
        if (null == bitmap) {
            Drawable background = /*getApplicationContext().*/getResources().getDrawable(R.drawable.big);
            BitmapDrawable bd = (BitmapDrawable) background;
            bitmap = bd.getBitmap();
        }
        mImageView.setImageBitmap(bitmap);
    }

    Handler mHandler = new Handler();

    boolean isClear;

    Runnable autoCleanRunable = new Runnable() {
        @Override
        public void run() {
            clearImage();
            isClear = true;
            Toast.makeText(ImageTestActivity.this, "自动清理成功", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();



    }

    @Override
    protected void onStart() {
        super.onStart();

        mHandler.removeCallbacks(autoCleanRunable);

        if (isClear) {
            isClear = false;
            showImage();
        }

    }
}
