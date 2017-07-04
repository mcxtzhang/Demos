package com.mcxtzhang.alyimagegallery.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mcxtzhang.alyimagegallery.R;
import com.mcxtzhang.alyimagegallery.gallery.view.AlyImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlyImageView iv1 = (AlyImageView) findViewById(R.id.iv1);
        AlyImageView iv2 = (AlyImageView) findViewById(R.id.iv2);
        AlyImageView iv3 = (AlyImageView) findViewById(R.id.iv3);
        AlyImageView iv4 = (AlyImageView) findViewById(R.id.iv4);
        AlyImageView iv5 = (AlyImageView) findViewById(R.id.iv5);


/*        iv1.show(((BitmapDrawable) getResources().getDrawable(R.drawable.test_100_100)).getBitmap());
        iv2.show(((BitmapDrawable) getResources().getDrawable(R.drawable.test_100_400)).getBitmap());
        iv3.show(((BitmapDrawable) getResources().getDrawable(R.drawable.test_400_100)).getBitmap());
        iv4.show(((BitmapDrawable) getResources().getDrawable(R.drawable.test_500_400)).getBitmap());
        iv5.show(((BitmapDrawable) getResources().getDrawable(R.drawable.test_100_3000)).getBitmap());*/
        iv1.show(R.drawable.test_100_100);
        iv2.show(R.drawable.test_100_400);
        iv3.show(R.drawable.test_400_100);
        iv4.show(R.drawable.test_500_400);
        iv5.show(R.drawable.test_100_3000);


    }
}
