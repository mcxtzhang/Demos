package com.mcxtzhang.github.gles.render.hockey.programs;

import android.content.Context;
import android.opengl.GLES20;

public class ColorShaderProgram extends ShaderProgram {

    private static final String vertexShaderCode =
            "precision mediump float;\n" +
                    "uniform mat4 u_Matrix;\n" +

                    "attribute vec4 a_Position;\n" +
                    "attribute vec4 a_Color;\n" +

                    "varying vec4 v_Color;\n" +

                    "void main() {\n" +
                    "    v_Color = a_Color;\n" +
                    "    gl_Position = u_Matrix * a_Position;\n" +
                    "    gl_PointSize = 10.0;\n" +

                    "}";

    //使用uniform ，用单一的颜色绘制物体
    private  static final String fragmentShaderCode =
            "precision mediump float;\n" +

                    "varying vec4 v_Color;\n" +

                    "void main() {\n" +
                    "    gl_FragColor = v_Color;\n" +
                    "}";

    private final int uMatrixLocation;

    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, vertexShaderCode, fragmentShaderCode);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }

}
