package com.mcxtzhang.github.gles.render.hockey;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SecondHockeyRender implements GLSurfaceView.Renderer {


    //varying是一个特殊的变量类型，它把给它的那些值进行混合，并把这些混合后的值发送给片段着色器；
    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "attribute vec4 a_Position;\n" +
                    "void main() {\n" +
                    "    gl_Position = a_Position;\n" +
                    "    gl_PointSize = 10.0;\n" +

                    "}";

    //使用uniform ，用单一的颜色绘制物体
    private String fragmentShaderCode =
            "precision mediump float;\n" +
                    "uniform vec4 a_Color;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = a_Color;\n" +
                    "}";

    public static final int POSITION_COMPONENT_COUNT = 2;

    private float[] vertexAttrArray = {
            //Order of coordinates:x,y,R,G,B

            //Triangle Fan
            0, 0,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            -0.5f, -0.5f,

            //line 1
            -0.5f, 0f,
            0.5f, 0f,

            //2 mallets
            0f, -0.25f,
            0f, 0.25f,

            //ball
            0, 0
    };

    public static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexDataBuffer;


    private int aPositionLocation;
    private int aColorLocation;

    public SecondHockeyRender() {
        vertexDataBuffer = ByteBuffer.allocateDirect(vertexAttrArray.length * Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexDataBuffer.put(vertexAttrArray).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int programId = ShaderHelper.buildProgram(vertexShaderCode, fragmentShaderCode);
        GLES20.glUseProgram(programId);

        //通过getxxxlocation 获取 代码里定义的参数
        aColorLocation = GLES20.glGetUniformLocation(programId, "a_Color");
        aPositionLocation = GLES20.glGetAttribLocation(programId, "a_Position");

        //vertex 赋值用 vertex开头的API，fragment赋值用  glUniform之类的，根据fragment shader code里的定义来
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexDataBuffer);
        //vertex 里的参数使用前都需要enable，fragment里的不需要； 实测发现好像 vec4 需要，
        GLES20.glEnableVertexAttribArray(aPositionLocation);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0, 0f, 0f);
        GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));


        GLES20.glUniform4f(aColorLocation, 1, 1, 1, 1);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        GLES20.glUniform4f(aColorLocation, 1, 0, 0, 1);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);


        GLES20.glUniform4f(aColorLocation, 0, 0, 1, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glUniform4f(aColorLocation, 1, 0, 0, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


        //冰球
        GLES20.glUniform4f(aColorLocation, 0, 1, 0, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 10, 1);
    }
}
