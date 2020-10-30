package com.mcxtzhang.github.gles.texturedemo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

public class FirstTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    public FirstTextureView(Context context) {
        super(context);
        init(context);
    }

    public FirstTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public FirstTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        Canvas canvas = lockCanvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawRect(new Rect(0, 0, 200, 200), paint);
//        unlockCanvasAndPost(canvas);

        new RenderThread(surface).start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
