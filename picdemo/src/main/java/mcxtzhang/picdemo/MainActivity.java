package mcxtzhang.picdemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "iv = [" + iv.getWidth() + "]");
                Log.i("TAG", "iv = [" + iv.getHeight() + "]");

                printBitmapSize(iv);
            }
        });
    }


    private void printBitmapSize(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            //bitmap的大小和控件大小无关的
            Log.d("TAG", " bitmap width = " + bitmap.getWidth() + " height = " + bitmap.getHeight());
            Log.d("TAG", " 大小 = " + bitmap.getByteCount());
        } else {
            Log.d("TAG", "Drawable is null !");
        }
    }
}
