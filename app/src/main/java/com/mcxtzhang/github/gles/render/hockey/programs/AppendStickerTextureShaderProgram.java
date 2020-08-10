package com.mcxtzhang.github.gles.render.hockey.programs;

import android.content.Context;
import android.opengl.GLES20;

public class AppendStickerTextureShaderProgram extends ShaderProgram {
    public static final String mVertexShaderCode =
            "precision mediump float;\n" +
                    "uniform mat4 u_Matrix;\n" +

                    "attribute vec4 a_Position;\n" +
                    "attribute vec2 a_TextureCoordinates;\n" +
                    //因为纹理有两个分量，s坐标和T坐标；所以被定义为一个vec2；

                    "varying vec2 v_TextureCoordinates;\n" +

                    "void main() {\n" +
                    "    gl_Position = u_Matrix * a_Position;\n" +
                    "    v_TextureCoordinates = a_TextureCoordinates;\n" +

                    "}";

    //为了把纹理绘制到一个物体上，OpenGl会为每个片段都调用片段着色器；
    //并且每个调用都接收 v_TextureCoordinates 纹理坐标。
    //片段着色器也通过 uniform u_TextureUnit 接收实际的纹理数据，它被定义为一个sampler2D，这个变量类型指的是一个二位纹理数据的数组
    //根据 被插值的纹理坐标 和 纹理数据，texture2D()方法会根据特定纹理坐标读取纹理中对应坐标的颜色值； 然后赋值给gl_FragColor
    public static final String mFragmentShaderCode =
            "precision mediump float;\n" +

                    "uniform sampler2D u_TextureUnit;\n" +
                    "varying vec2 v_TextureCoordinates;\n" +

                    "void main() {\n" +
                    "    gl_FragColor = texture2D(u_TextureUnit,v_TextureCoordinates);\n" +
                    "}";

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;
    private final int aTextureCooridnatesLocation;

    public AppendStickerTextureShaderProgram(Context context) {
        super(context, mVertexShaderCode, mFragmentShaderCode);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCooridnatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatsAttributeLocation() {
        return aTextureCooridnatesLocation;
    }
}
