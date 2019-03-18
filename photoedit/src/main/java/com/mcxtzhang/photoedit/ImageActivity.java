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
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kuan);
                mCropImageView.setImageBitmap(bitmap);
                mCropImageView.showBitmapInCenter();
                mCropImageView.setCropDragView(mCropDragView);

                mCropDragView.bindCropImageView(mCropImageView)
                        .setCropRate(CropDragView.CROP_RATE_FREE);
            }
        }, 500);

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.setImageBitmap(mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
            }
        });


        findViewById(R.id.tvModeNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropDragView.setCropRate(CropDragView.CROP_RATE_FREE);
            }
        });
        findViewById(R.id.tvMode11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropDragView.setCropRate(CropDragView.CROP_RATE_11);
            }
        });

        findViewById(R.id.tvMode34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropDragView.setCropRate(CropDragView.CROP_RATE_34);
            }
        });
        findViewById(R.id.tvMode43).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropDragView.setCropRate(CropDragView.CROP_RATE_43);
            }
        });

        findViewById(R.id.tvRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.rotate();
            }
        });

    }


}
