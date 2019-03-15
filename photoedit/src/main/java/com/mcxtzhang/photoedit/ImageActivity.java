package com.mcxtzhang.photoedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.photoedit.widget.CropDragView;
import com.mcxtzhang.photoedit.widget.CropImageView;

public class ImageActivity extends AppCompatActivity {
    CropImageView mCropImageView;

    CropDragView mCropDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mCropImageView = findViewById(R.id.zoomImageView);

        mCropDragView = findViewById(R.id.cropView);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.long1);
                mCropImageView.setImageBitmap(bitmap);
                mCropImageView.showBitmapInCenter();
                mCropImageView.setCropDragView(mCropDragView);

                mCropDragView.bindCropImageView(mCropImageView);
            }
        }, 500);

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.setImageBitmap(mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
            }
        });


    }


}
