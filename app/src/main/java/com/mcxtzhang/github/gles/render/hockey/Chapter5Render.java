package com.mcxtzhang.github.gles.render.hockey;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.mcxtzhang.github.gles.render.hockey.util.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Chapter5Render implements GLSurfaceView.Renderer {


    //varying是一个特殊的变量类型，它把给它的那些值进行混合，并把这些混合后的值发送给片段着色器；
    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "uniform mat4 u_Matrix;\n" +
                    "attribute vec4 a_Position;\n" +
                    "attribute vec4 aa_Color;\n" +
                    "varying vec4 v_Color;\n" +
                    "void main() {\n" +
                    "    v_Color = aa_Color;\n" +
                    "    gl_Position = u_Matrix * a_Position;\n" +
                    "    gl_PointSize = 10.0;\n" +

                    "}";

    //使用uniform ，用单一的颜色绘制物体
    private String fragmentShaderCode =
            "precision mediump float;\n" +
                    "varying vec4 v_Color;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = v_Color;\n" +
                    "}";

    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int COLOR_COMPONENT_COUNT = 3;
    public static final int BYTES_PER_FLOAT = 4;
    public static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private float[] vertexAttrArray = {
            //Order of coordinates:x,y,R,G,B

            //Triangle Fan
            0, 0, 1f, 1f, 1f,
            -0.25f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.25f, -0.5f, 0.7f, 0.7f, 0.7f,
            0.25f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.25f, 0.5f, 0.7f, 0.7f, 0.7f,
            -0.25f, -0.5f, 0.7f, 0.7f, 0.7f,

            //line 1
            -0.25f, 0f, 1f, 0f, 0f,
            0.25f, 0f, 1f, 0f, 0f,

            //2 mallets
            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f,

            //ball
            0, 0
    };

    private FloatBuffer vertexDataBuffer;


    private  float[] projectionMatrix = new float[16];


    private int aPositionLocation;
    private int aaColorLocation;
    private int uMatrixLocation;

    public Chapter5Render() {
        vertexDataBuffer = ByteBuffer.allocateDirect(vertexAttrArray.length * Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int programId = ShaderHelper.buildProgram(vertexShaderCode, fragmentShaderCode);
        GLES20.glUseProgram(programId);

        //通过getxxxlocation 获取 代码里定义的参数
        aPositionLocation = GLES20.glGetAttribLocation(programId, "a_Position");

        vertexDataBuffer.put(vertexAttrArray).position(0);

        //vertex 赋值用 vertex开头的API，fragment赋值用  glUniform之类的，根据fragment shader code里的定义来
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexDataBuffer);
        //vertex 里的参数使用前都需要enable，fragment里的不需要； 实测发现好像 vec4 需要，
        GLES20.glEnableVertexAttribArray(aPositionLocation);


        aaColorLocation = GLES20.glGetAttribLocation(programId, "aa_Color");
        vertexDataBuffer.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aaColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexDataBuffer);
        GLES20.glEnableVertexAttribArray(aaColorLocation);


        uMatrixLocation = GLES20.glGetUniformLocation(programId, "u_Matrix");

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float aspectRatio = width > height ?
                (float) (width) / height :
                (float) height / width;

        aspectRatio = aspectRatio * 1.5f;//缩小

//        if ((width > height)) {
//            //横屏
//            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1.5f, 1.5f, -1, 1);
//        } else {
//            Matrix.orthoM(projectionMatrix, 0, -1.5f + 1f, 1.5f + 1f, -aspectRatio + 1f, aspectRatio + 1f, -1, 1);
//        }

        projectionMatrix =  new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        };

        //Matrix.rotateM(projectionMatrix, 0, 90, 0F, 0F, 1F);

        //Matrix.scaleM(projectionMatrix, 0, 1.5f, 1.5f, 1);
//        Matrix.translateM(projectionMatrix, 0, 0.5f, 0.5f, 0);
//        Matrix.translateM(projectionMatrix, 0, 0.5f, 0.5f, 0);
        //Matrix.translateM(projectionMatrix, 0, 1, 1, 0);
        Matrix.translateM(projectionMatrix, 0, 0, 0, 0);



    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0, 0f, 0f);
        GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));


        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);


        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


        //冰球
        GLES20.glDrawArrays(GLES20.GL_POINTS, 10, 1);
    }
}
