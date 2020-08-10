package com.mcxtzhang.github.gles.render.hockey;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.mcxtzhang.github.R;
import com.mcxtzhang.github.gles.render.hockey.objects.AppendSticker;
import com.mcxtzhang.github.gles.render.hockey.objects.Mallet;
import com.mcxtzhang.github.gles.render.hockey.objects.Table;
import com.mcxtzhang.github.gles.render.hockey.programs.AppendStickerTextureShaderProgram;
import com.mcxtzhang.github.gles.render.hockey.programs.ColorShaderProgram;
import com.mcxtzhang.github.gles.render.hockey.programs.TextureShaderProgram;
import com.mcxtzhang.github.gles.render.hockey.util.TextureUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Chapter7Render implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mMallet;
    private AppendSticker mAppendSticker;
    private AppendStickerTextureShaderProgram mAppendStickerProgram;
    private int mAppendTextureId;

    private TextureShaderProgram mTextureShaderProgram;
    private ColorShaderProgram mColorShaderProgram;

    private int texture;

    public Chapter7Render(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 0f);

        table = new Table();
        mMallet = new Mallet();

        mAppendSticker = new AppendSticker();
        mAppendStickerProgram = new AppendStickerTextureShaderProgram(context);
        mAppendTextureId = TextureUtils.loadTexture(BitmapFactory.decodeResource(context.getResources(), R.drawable.buy_icon_zaned));

        mTextureShaderProgram = new TextureShaderProgram(context);
        mColorShaderProgram = new ColorShaderProgram(context);

        texture = TextureUtils.loadTexture(BitmapFactory.decodeResource(context.getResources(), R.drawable.big_image));


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float aspectRatio = width > height ?
                (float) (width) / height :
                (float) height / width;

        aspectRatio = aspectRatio * 1.5f;//缩小

        if ((width > height)) {
            //横屏
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1.5f, 1.5f, -1, 1);
        } else {
            Matrix.orthoM(projectionMatrix, 0, -1.5f /*+ 1f*/, 1.5f /*+ 1f*/, -aspectRatio /*+ 1f*/, aspectRatio /*+ 1f*/, -1, 1);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(projectionMatrix, texture);
        table.bindData(mTextureShaderProgram);
        table.draw();


        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(projectionMatrix);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();

        mAppendStickerProgram.useProgram();
        mAppendSticker.bindData(mAppendStickerProgram);

        mAppendStickerProgram.setUniforms(projectionMatrix, mAppendTextureId);
        mAppendSticker.draw();
    }
}
