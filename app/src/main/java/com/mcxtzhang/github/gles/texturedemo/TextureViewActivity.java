package com.mcxtzhang.github.gles.texturedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class TextureViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FirstTextureView(this));

    }
}
