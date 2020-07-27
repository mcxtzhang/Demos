package com.mcxtzhang.github.gles.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class changeDrawModeRender implements GLSurfaceView.Renderer {
    private static final String TAG = "changeDrawModeRender";
    private int glSurfaceViewWidth;
    private int glSurfaceViewHeight;
    private int mDrawMode;

    public int getDrawMode() {
        return mDrawMode;
    }

    public changeDrawModeRender setDrawMode(int drawMode) {
        mDrawMode = drawMode;
        return this;
    }

    /**
     * 在OpenGL ES 2.0中，attribute就是输入，varying就是从vertex shader往fragment shader的输出
     */
    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "attribute vec4 a_Position;\n" +
                    "attribute vec4 a_Color;\n" +
                    "varying vec4 v_Color;\n" +
                    "void main() {\n" +
                    "    gl_Position = a_Position ;\n" +
                    "    v_Color = a_Color ;\n" +
                    "}";

    private String fragmentShaderCode =
            "precision mediump float;\n" +
                    "varying vec4 v_Color;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = v_Color;\n" +
                    "}";


    // 颜色数据
    // The color data
    private float[] colorData = new float[]{
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f};
    // 每个颜色的成份数（RGBA）
    // The num of components of per color(RGBA)
    private int COLOR_COMPONENT_COUNT = 4;


    // 三角形顶点数据
    // The vertex data of a triangle
    float[] vertexData = new float[]{
            0f, 0f,
            -0.5f, 0f,
            0, 0.5f,
            0.5f, 0,
            0, -0.5f,
            -0.5f, -0.5f};
    private int VERTEX_COMPONENT_COUNT = 2;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e(TAG, "onSurfaceCreated() called with: gl = [" + gl + "], config = [" + config + "]");
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


        // 将三角形顶点数据放入buffer中
        // Put the triangle vertex data into the buffer
        FloatBuffer buffer = ByteBuffer.allocateDirect(vertexData.length * Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        buffer.put(vertexData)
                .position(0);

        // 获取字段a_Position在shader中的位置
        int location = GLES20.glGetAttribLocation(programId, "a_Position");
        // 启动对应位置的参数
        GLES20.glEnableVertexAttribArray(location);
        // 指定a_Position所使用的顶点数据
        GLES20.glVertexAttribPointer(location, VERTEX_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, buffer);


        // 将三角形顶点数据放入buffer中
        FloatBuffer colorDataBuffer = ByteBuffer.allocateDirect(colorData.length * Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        colorDataBuffer.put(colorData)
                .position(0);

        int a_color = GLES20.glGetAttribLocation(programId, "a_Color");
        GLES20.glEnableVertexAttribArray(a_color);
        GLES20.glVertexAttribPointer(a_color, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, colorDataBuffer);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged() called with: gl = [" + gl + "], width = [" + width + "], height = [" + height + "]");
        // 记录GLSurfaceView的宽高
        // Record the width and height of the GLSurfaceView
        glSurfaceViewWidth = width;
        glSurfaceViewHeight = height;

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i(TAG, "onDrawFrame() called with: gl = [" + gl + "],mDrawMode:" + mDrawMode);
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1f);
        GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));

        // 设置视口，这里设置为整个GLSurfaceView区域
        // Set the viewport to the full GLSurfaceView
        GLES20.glViewport(0, 0, glSurfaceViewWidth, glSurfaceViewHeight);

        /**
         * https://juejin.im/post/5cbb1787e51d456e46603e2d
         * GL_TRIANGLES就是我们之前的例子中一直在用的，它是将每三个独立顶点构成一个三角形，不同三角形之间不会共用顶点
         *
         * GL_TRIANGLE_STRIP的效果就像它名字一样，是带状的，它构成三角形的规则和顶点编号的奇偶有关，
         * 如果当前顶点编号是奇数，则三角形顶点顺序是k-1, k-2, k，
         * 如果顶点数量是偶数，则三角形顶点顺序为k-2, k-1, k，
         * 这是什么意思呢？我们对照上图来看，对于2号点，那么就是(v0, v1, v2)对于3号点，那么就是(v2, v1, v3)，可以看到它三角形顶点是会共用的，并且我们能看到，每个三角形都是同一个方向的旋转。
         *
         *
         */
        GLES20.glDrawArrays(mDrawMode == 0 ? GLES20.GL_TRIANGLES : mDrawMode, 0, 6);

        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 3, 3);

    }
}
