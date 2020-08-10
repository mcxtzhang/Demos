package com.mcxtzhang.github.gles.render.hockey.objects;

import android.opengl.GLES20;

import com.mcxtzhang.github.gles.render.hockey.data.VertexArray;
import com.mcxtzhang.github.gles.render.hockey.programs.AppendStickerTextureShaderProgram;

public class AppendSticker {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final float[] VERTEX_DATA = {
            //X,Y,
            //Triangle Fan
            0, 0,
            -0.25f, -0.8f,
            0.25f, -0.8f,
            0.25f, 0.8f,
            -0.25f, 0.8f,
            -0.25f, -0.8f,

    };
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    //S,Y
    private static final float[] TEXTURE_DATA = {
            0.5f, 0.5f,
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f,
            0f, 1f,
    };

    private VertexArray mVertexArray;
    private VertexArray mTextureArray;


    public AppendSticker() {
        mVertexArray = new VertexArray(VERTEX_DATA);
        mTextureArray = new VertexArray(TEXTURE_DATA);
    }

    public void bindData(AppendStickerTextureShaderProgram textureProgram) {
        mVertexArray.setVertexAttribPointer(0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                0);

        mTextureArray.setVertexAttribPointer(0,
                textureProgram.getTextureCoordinatsAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                0);
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}

