package com.mcxtzhang.github.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mcxtzhang.github.R;
import com.mcxtzhang.github.util.BitmapUtils;

public class BitmapActivity extends AppCompatActivity {
    private static final String TAG = "TAG3";
    ImageView image;
    ImageView image2, image3, image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        //1 wrap_content 的ImageView,通过src设置bitmap， bitmap=ImageView 的宽高 大小 会随着图片放不同的分辨率而改变。
        (image = (ImageView) findViewById(R.id.image)).setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "TAG";

            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Log.d(TAG, "onClick() called with: drawingCache = [" + bitmap.getWidth() + "]" + bitmap.getHeight());
                Log.d(TAG, "onClick() called with: view = [" + view.getWidth() + "]" + view.getHeight());

                Log.d(TAG, "onClick() called with:   = [" + BitmapUtils.getBitmapSize(bitmap) + "]");
            }
        });

        //2 wrap_content 的ImageView ，通过 decode设置Bitmap。bitmap=ImageView 的宽高 大小 会随着图片放不同的分辨率而改变。
        image2 = (ImageView) findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "TAG2";

            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image2.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Log.d(TAG, "onClick() called with: drawingCache = [" + bitmap.getWidth() + "]" + bitmap.getHeight());
                Log.d(TAG, "onClick() called with: view = [" + view.getWidth() + "]" + view.getHeight());

                Log.d(TAG, "onClick() called with:   = [" + BitmapUtils.getBitmapSize(bitmap) + "]");
            }
        });

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.lint1);
        Log.d(TAG, "bitmap = [" + bitmap.getWidth() + "]" + bitmap.getHeight());
        Log.d(TAG, "bitmap:   = [" + BitmapUtils.getBitmapSize(bitmap) + "]");
        image2.setImageBitmap(bitmap);


        //3 固定尺寸的ImageView，通过设置src设置Bitmap。  bitmap不等于ImageView 的宽高 大小 会随着图片放不同的分辨率而改变。
        (image3 = (ImageView) findViewById(R.id.image3)).setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "TAG";

            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image3.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Log.d(TAG, "onClick() called with: drawingCache = [" + bitmap.getWidth() + "]" + bitmap.getHeight());
                Log.d(TAG, "onClick() called with: view = [" + view.getWidth() + "]" + view.getHeight());

                Log.d(TAG, "onClick() called with:   = [" + BitmapUtils.getBitmapSize(bitmap) + "]");
            }
        });
        //4 固定尺寸的ImageView，通过decode设置Bitmap。 bitmap不等于ImageView 的宽高 大小 会随着图片放不同的分辨率而改变。
        image4 = (ImageView) findViewById(R.id.image4);
        image4.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "TAG2";

            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) image4.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                Log.d(TAG, "onClick() called with: drawingCache = [" + bitmap.getWidth() + "]" + bitmap.getHeight());
                Log.d(TAG, "onClick() called with: view = [" + view.getWidth() + "]" + view.getHeight());

                Log.d(TAG, "onClick() called with:   = [" + BitmapUtils.getBitmapSize(bitmap) + "]");
            }
        });

        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.lint1);
        Log.d(TAG, "bitmap4 = [" + bitmap4.getWidth() + "]" + bitmap4.getHeight());
        Log.d(TAG, "bitmap4:   = [" + BitmapUtils.getBitmapSize(bitmap4) + "]");
        image4.setImageBitmap(bitmap4);

    }
}
