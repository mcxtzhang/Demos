package com.mcxtzhang.github.gles.render.hockey.objects;

import android.opengl.GLES20;

import com.mcxtzhang.github.gles.render.hockey.data.VertexArray;
import com.mcxtzhang.github.gles.render.hockey.programs.TextureShaderProgram;

import static com.mcxtzhang.github.gles.render.hockey.util.Constants.BYTES_PER_FLOAT;

public class Table {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA={
      //X,Y,S,Y


            //Triangle Fan

                0,    0,        0.5f,0.5f,
            -0.5f,-0.8f,          0f,0.9f,
             0.5f,-0.8f,          1f,0.9f,
             0.5f, 0.8f,          1f,0.1f,
            -0.5f, 0.8f,          0f,0.1f,
            -0.5f,-0.8f,          0f,0.9f,

    };

    private final VertexArray mVertexArray;
    public Table(){
        mVertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram){
        mVertexArray.setVertexAttribPointer(0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        mVertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatsAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);
    }
}
