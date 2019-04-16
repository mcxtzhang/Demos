package com.mcxtzhang.photoedit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.photoedit.widget.CropDragView;
import com.mcxtzhang.photoedit.widget.CropImageView;
import com.mcxtzhang.photoedit.widget.UGCPhotoCropRotateModel;
import com.mcxtzhang.photoedit.widget.UgcCropView;

public class ImageActivity extends AppCompatActivity {
    private boolean isLoading;
    private boolean isCroping;

    private CropImageView mCropImageView;
    private CropDragView mCropDragView;
    private UgcCropView mUgcCropView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mUgcCropView = findViewById(R.id.ugcCropView);
        mCropImageView = mUgcCropView.getCropImageView();
        mCropDragView = mUgcCropView.getCropDragView();

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kuan);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                UGCPhotoCropRotateModel photoCropRotateModel = (UGCPhotoCropRotateModel) getIntent().getParcelableExtra("data");
                mCropImageView.showBitmapInCenter(bitmap, photoCropRotateModel);
                mCropImageView.setCropDragView(mCropDragView);

                mCropDragView.bindCropImageView(mCropImageView, photoCropRotateModel);

                if (null == photoCropRotateModel) {
                    mCropDragView.setCropRate(CropDragView.CROP_RATE_FREE);
                    mModeNormal.setSelected(true);
                } else {
                    mCropDragView.setCropRate(photoCropRotateModel.cropRate);
                    setCropRateStatusUI(photoCropRotateModel.cropRate);
                }

                if (null == photoCropRotateModel) {
                    return;
                }
                double rotate = photoCropRotateModel.rotate;
                if (rotate == 0) {
                } else if (rotate == -90) {
                    //顺时针90度
                    mCropImageView.rotate();
                } else if (rotate == 180) {
                    mCropImageView.rotate();
                    mCropImageView.rotate();
                } else if (rotate == 90) {

                    mCropImageView.rotate();
                    mCropImageView.rotate();
                    mCropImageView.rotate();
                }
            }
        }, 500);

        findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("photo_crop", mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
                setResult(RESULT_OK, intent);
                finish();

                //mCropImageView.setImageBitmap(mCropImageView.crop(mCropDragView.getStartX(), mCropDragView.getStartY(), mCropDragView.getCropWidth(), mCropDragView.getCropHeight()));
            }
        });


        mModeNormal = findViewById(R.id.tvModeNormal);
        mMode11 = findViewById(R.id.tvMode11);
        mMode34 = findViewById(R.id.tvMode34);
        mMode43 = findViewById(R.id.tvMode43);

        findViewById(R.id.tvModeNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                changeCropRateStatus(CropDragView.CROP_RATE_FREE);
            }
        });
        findViewById(R.id.tvMode11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                changeCropRateStatus(CropDragView.CROP_RATE_11);
            }
        });

        findViewById(R.id.tvMode34).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                changeCropRateStatus(CropDragView.CROP_RATE_34);
            }
        });
        findViewById(R.id.tvMode43).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    return;
                }
                changeCropRateStatus(CropDragView.CROP_RATE_43);
            }
        });


        findViewById(R.id.tvRotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.rotate(true);
                if (mCropDragView.getCropRate() == CropDragView.CROP_RATE_34) {
                    resetCropRateStatusUI();
                    mMode43.setSelected(true);
                    mCropDragView.setCropRate(CropDragView.CROP_RATE_43, false);
                } else if (mCropDragView.getCropRate() == CropDragView.CROP_RATE_43) {
                    resetCropRateStatusUI();
                    mMode34.setSelected(true);
                    mCropDragView.setCropRate(CropDragView.CROP_RATE_34, false);
                }
            }
        });

        findViewById(R.id.tvRestore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kuan);
                mCropImageView.reset();

                mCropDragView
                        .setCropRate(CropDragView.CROP_RATE_FREE)
                        .initCropAreaPosition();
                resetCropRateStatusUI();
                mModeNormal.setSelected(true);
            }
        });

    }

    private View mModeNormal, mMode11, mMode34, mMode43;

    private void changeCropRateStatus(int cropRate) {
        resetCropRateStatusUI();
        setCropRateStatusUI(cropRate);

        mCropDragView.setCropRate(cropRate, true);
    }

    private void setCropRateStatusUI(int cropRate) {
        switch (cropRate) {
            case CropDragView.CROP_RATE_FREE:
                mModeNormal.setSelected(true);
                break;
            case CropDragView.CROP_RATE_11:
                mMode11.setSelected(true);
                break;
            case CropDragView.CROP_RATE_34:
                mMode34.setSelected(true);
                break;
            case CropDragView.CROP_RATE_43:
                mMode43.setSelected(true);
                break;
        }
    }

    private void resetCropRateStatusUI() {
        mModeNormal.setSelected(false);
        mMode11.setSelected(false);
        mMode34.setSelected(false);
        mMode43.setSelected(false);
    }
}
