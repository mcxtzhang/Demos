package com.mcxtzhang.photoedit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.photoedit.widget.ZoomImageView;

public class ImageActivity extends AppCompatActivity {
    ZoomImageView mZoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mZoomImageView =
                findViewById(R.id.zoomImageView);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mZoomImageView.setImageBitmap(mZoomImageView.clip());

            }
        });
    }



}
