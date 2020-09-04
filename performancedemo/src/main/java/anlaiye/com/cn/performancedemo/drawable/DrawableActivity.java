package anlaiye.com.cn.performancedemo.drawable;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import anlaiye.com.cn.performancedemo.R;

public class DrawableActivity extends AppCompatActivity {
    private static final String TAG = "DrawableActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("DrawableActivity", "onCreate() called with: metrics = [" + metrics.densityDpi + "]");


        Glide.with(this)
               // .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3373849050,246075620&fm=26&gp=0.jpg")
               // .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2126677482,734469783&fm=26&gp=0.jpg")
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599217734897&di=a505089e59d0b77acd344d21c3c86625&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F01%2F99%2F30%2F598471c9d2119_610.jpg")
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.d(TAG, "onResourceReady() called with: resource = [" + resource
                                + "], resource.getWidth() = [" + resource.getWidth() + "]"
                                + "], resource.getHeight() = [" + resource.getHeight() + "]"
                                + "], resource.getAllocationByteCount() = [" + resource.getConfig().toString()+ "]");

                        ((ImageView) findViewById(R.id.testImage1)).setImageBitmap(resource);


                    }
                });


    }
}
