package com.mcxtzhang.github.gles;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HelloWorldRender implements GLSurfaceView.Renderer {
    private int glSurfaceViewWidth;
    private int glSurfaceViewHeight;

    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "attribute vec4 a_Position;\n" +
                    "void main() {\n" +
                    "    gl_Position = a_Position;\n" +
                    "}";

    private String fragmentShaderCode =
            "precision mediump float;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);\n" +
                    "}";


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 创建GL程序
        // Create GL program
        int programId = GLES20.glCreateProgram();
        // 加载、编译vertex shader和fragment shader
        // Load and compile vertex shader and fragment shader
        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(vertexShader, vertexShaderCode);
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode);
        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);


        // 将shader程序附着到GL程序上
        // Attach the compiled shaders to the GL program
        GLES20.glAttachShader(programId, vertexShader);
        GLES20.glAttachShader(programId, fragmentShader);

        // 链接GL程序
        // Link the GL program
        GLES20.glLinkProgram(programId);
        // 应用GL程序
        // Use the GL program
        GLES20.glUseProgram(programId);


        // 三角形顶点数据
        // The vertex data of a triangle
        float[] vertexData = new float[]{
                0f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f};

// 将三角形顶点数据放入buffer中
// Put the triangle vertex data into the buffer
        FloatBuffer buffer = ByteBuffer.allocateDirect(vertexData.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        buffer.put(vertexData);
        buffer.position(0);


        // 获取字段a_Position在shader中的位置
        // Get the location of a_Position in the shader
        int location = GLES20.glGetAttribLocation(programId, "a_Position");
        // 启动对应位置的参数
        // Enable the parameter of the location
        GLES20.glEnableVertexAttribArray(location);

        // 指定a_Position所使用的顶点数据
        // Specify the vertex data of a_Position
        GLES20.glVertexAttribPointer(location, 2, GLES20.GL_FLOAT, false, 0, buffer);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 记录GLSurfaceView的宽高
        // Record the width and height of the GLSurfaceView
        glSurfaceViewWidth = width;
        glSurfaceViewHeight = height;

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1f);
        GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));

        // 设置视口，这里设置为整个GLSurfaceView区域
        // Set the viewport to the full GLSurfaceView
        GLES20.glViewport(0, 0, glSurfaceViewWidth, glSurfaceViewHeight);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

    }
}
