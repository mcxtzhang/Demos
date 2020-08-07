package com.mcxtzhang.github.gles.render.hockey.objects;

import android.opengl.GLES20;

import com.mcxtzhang.github.gles.render.hockey.data.VertexArray;
import com.mcxtzhang.github.gles.render.hockey.programs.ColorShaderProgram;
import com.mcxtzhang.github.gles.render.hockey.util.Constants;

//木槌
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
                    * Constants.BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
            //order of coordinates: x,y,      r,g,b
            0F,-0.4F,       0F,0F,1F,
            0F, 0.4F,       1F,0F,0F,
    };

    private final VertexArray mVertexArray;

    public Mallet(){
        mVertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        mVertexArray.setVertexAttribPointer(
                0,
                colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        mVertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                colorShaderProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,2);
    }

}
