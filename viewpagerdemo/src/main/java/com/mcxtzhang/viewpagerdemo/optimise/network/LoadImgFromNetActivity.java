package com.mcxtzhang.viewpagerdemo.optimise.network;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mcxtzhang.viewpagerdemo.R;
import com.mcxtzhang.viewpagerdemo.optimise.ImageTestActivity;

public class LoadImgFromNetActivity extends ImageTestActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.get(LoadImgFromNetActivity.this).clearMemory();
            }
        });
    }

    @Override
    protected void clearImage() {
        mImageView.setImageBitmap(null);
    }

    @Override
    protected void showImage() {
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492149677960&di=98fbca8547260880a17d2162923847cc&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F5d8ea144ad345982913052140cf431adcaef8455.jpg")
                .into(mImageView);
    }
}
