package com.mcxtzhang.photoedit;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.image);

        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                bitmap = bitmapDrawable.getBitmap();


                int imageViewWidth = imageView.getWidth();
                int imageViewHeight = imageView.getHeight();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float scaleWidth = ((float) imageViewWidth) / width;
                float scaleHeight = ((float) imageViewHeight) / height;
                Matrix imageMatrix = imageView.getImageMatrix();
                imageMatrix.postScale(scaleWidth, scaleHeight);
                imageView.setImageMatrix(imageMatrix);
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                bitmap = bitmapDrawable.getBitmap();

                bitmap = PhotoEditUtils.rotaingImageView(90, bitmap);
                imageView.setImageBitmap(bitmap);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
