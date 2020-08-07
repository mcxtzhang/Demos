package com.mcxtzhang.github.gles.render.hockey.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.mcxtzhang.github.gles.render.hockey.util.ShaderHelper;

public class ShaderProgram {


    static final String U_MATRIX = "u_Matrix";
    static final String U_TEXTURE_UNIT = "u_TextureUnit";

    static final String A_POSITION = "a_Position";
    static final String A_COLOR = "a_Color";
    static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    public ShaderProgram(Context context, String vertexShaderCode, String fragmentShaderCode) {
        program = ShaderHelper.buildProgram(vertexShaderCode, fragmentShaderCode);
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
