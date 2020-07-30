package com.mcxtzhang.github.gles.render;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FrameButterRender implements GLSurfaceView.Renderer {

    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "attribute vec4 a_position;\n" +
                    "attribute vec2 a_textureCoordinate;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "void main() {\n" +
                    "    v_textureCoordinate = a_textureCoordinate;\n" +
                    "    gl_Position = a_position;\n" +
                    "}\n";
    private String fragmentShaderCode1 =
            "precision mediump float;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    "    vec4 color = texture2D(u_texture, v_textureCoordinate);\n" +
                    "    color.b = 0.5;\n" +
                    "    gl_FragColor = color;\n" +
                    "}\n";
    private String fragmentShaderCode2 =
            "precision mediump float;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    "    float offset = 0.005;\n" +
                    "    vec4 color = texture2D(u_texture, v_textureCoordinate) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x - offset, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x + offset, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x - offset * 2.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x + offset * 2.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x - offset * 3.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x + offset * 3.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x - offset * 4.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    color += texture2D(u_texture, vec2(v_textureCoordinate.x + offset * 4.0, v_textureCoordinate.y)) * 0.11111;\n" +
                    "    gl_FragColor = color;\n" +
                    "}\n";

    private String fragmentShaderCode3 =
            "precision mediump float;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    "    vec4 color = texture2D(u_texture, v_textureCoordinate);\n" +
                    "    color.r = 0.3;\n" +
                    "    gl_FragColor = color;\n" +
                    "}\n";


    // GLSurfaceView的宽高
    // The width and height of GLSurfaceView
    private int glSurfaceViewWidth = 0;
    private int glSurfaceViewHeight = 0;

    // 纹理顶点数据
    // The vertex data of the texture
    private float[] vertexData = new float[]{
            -1f, -1f,
            -1f, 1f,
            1f, 1f,

            -1f, -1f,
            1f, 1f,
            1f, -1f};
    private int VERTEX_COMPONENT_COUNT = 2;
    private FloatBuffer vertexDataBuffer;

    // 纹理坐标
    // The texture coordinate
    private float[] textureCoordinateData0 = new float[]{0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f, 1f, 1f};
    private float[] textureCoordinateData1 = new float[]{0f, 0f, 0f, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 1f, 0f};
    private float[] textureCoordinateData2 = new float[]{0f, 0f, 0f, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 1f, 0f};

    private FloatBuffer textureCoordinateDataBuffer0;
    private FloatBuffer textureCoordinateDataBuffer1;
    private FloatBuffer textureCoordinateDataBuffer2;
    private int TEXTURE_COORDINATE_COMPONENT_COUNT = 2;

    // 2个GL Program
    // two GL Programs
    private int programId1 = 0;
    private int programId2 = 0;
    private int programId3 = 0;

    // 帧缓存
    // frame buffer
    private int frameBuffer = 0;
    private int frameBuffer3 = 0;

    // 帧缓绑定的texture
    // the texture bind on the frame buffer
    private int frameBufferTexture = 0;
    private int frameBufferTexture3 = 0;

    // 图片texture
    // image texture
    private int imageTexture = 0;

    private Bitmap mBitmap;

    public FrameButterRender(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 初始化坐标、图片数据
        // Init the coordinates and image texture
        initData();


        // 创建2个GL Program，第一个用来做均值模糊，第二做普通纹理贴图
        programId1 = createGLProgram(vertexShaderCode, fragmentShaderCode1);
        programId2 = createGLProgram(vertexShaderCode, fragmentShaderCode2);
        programId3 = createGLProgram(vertexShaderCode, fragmentShaderCode3);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 记录GLSurfaceView的宽高
        // Record the width and height of the GLSurfaceView
        glSurfaceViewWidth = width;
        glSurfaceViewHeight = height;


        // 初始化frame buffer
        // Init the frame buffer
        initFrameBuffer(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // 绑定第0个GL Program
        bindGLProgram(programId1, imageTexture, textureCoordinateDataBuffer0);

        // 绑定frame buffer
        bindFrameBuffer(frameBuffer);

        // 执行渲染，渲染效果为将图片的蓝色通道全部设为0.5
        render();

        // 绑定第1个GL Program
        bindGLProgram(programId2, frameBufferTexture, textureCoordinateDataBuffer1);

        // 绑定0号frame buffer
        bindFrameBuffer(frameBuffer3);

        // 执行渲染，渲染效果水平方向的模糊
        render();


        // 绑定第1个GL Program
        bindGLProgram(programId3, frameBufferTexture3, textureCoordinateDataBuffer2);

        // 绑定0号frame buffer
        /**
         * 这里注意一点，0号frame buffer是一个特殊的frame buffer，它是默认的frame buffer，
         * 即如果我们没有使用glBindFramebuffer()去绑定过frame buffer，它就是绑定到0号frame buffer上的，
         * 0号frame buffer通常代表屏幕，离屏渲染除外，这个暂不讨论，现在大家只需要知道将frame buffer绑定到0就能渲染到屏幕上就行了。
         *
         */
        bindFrameBuffer(0);

        // 执行渲染，渲染效果水平方向的模糊
        render();

    }

    private void bindGLProgram(int programId, int texture, FloatBuffer textureCoordinateDataBuffer) {
        // 应用GL程序
        // Use the GL program
        GLES20.glUseProgram(programId);

        // 获取字段a_Position在shader中的位置
        int a_position = GLES20.glGetAttribLocation(programId, "a_position");
        // 启动对应位置的参数
        GLES20.glEnableVertexAttribArray(a_position);
        // 指定a_Position所使用的顶点数据
        GLES20.glVertexAttribPointer(a_position, 2, GLES20.GL_FLOAT, false, 0, vertexDataBuffer);

        /**
         * 纹理坐标的坐标原点在左下角，每个轴的范围是0~1，同样的也可以超出0和1，超出之后的表现会根据设置的纹理参数有所不同。
         */
        // 获取字段a_Position在shader中的位置
        int a_textureCoordinate = GLES20.glGetAttribLocation(programId, "a_textureCoordinate");
        // 启动对应位置的参数
        GLES20.glEnableVertexAttribArray(a_textureCoordinate);
        // 指定a_Position所使用的顶点数据
        GLES20.glVertexAttribPointer(a_textureCoordinate, 2, GLES20.GL_FLOAT, false, 0, textureCoordinateDataBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        int uTextureLocation = GLES20.glGetAttribLocation(programId, "u_texture");
        //然后给这个location指定对应哪个纹理单元，这里我们使用0号纹理单元：
        GLES20.glUniform1i(uTextureLocation, 0);
    }


    private void bindFrameBuffer(int frameBuffer) {
        // 绑定frame buffer
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
    }

    private void render() {
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1f);
        GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));
        GLES20.glViewport(0, 0, glSurfaceViewWidth, glSurfaceViewHeight);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexData.length / VERTEX_COMPONENT_COUNT);

    }

    private void initData() {
        vertexDataBuffer = ByteBuffer.allocateDirect(vertexData.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexDataBuffer.put(vertexData)
                .position(0);


        textureCoordinateDataBuffer0 = ByteBuffer.allocateDirect(textureCoordinateData0.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureCoordinateDataBuffer0.put(textureCoordinateData0)
                .position(0);

        textureCoordinateDataBuffer1 = ByteBuffer.allocateDirect(textureCoordinateData1.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureCoordinateDataBuffer1.put(textureCoordinateData1)
                .position(0);

        textureCoordinateDataBuffer2 = ByteBuffer.allocateDirect(textureCoordinateData2.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureCoordinateDataBuffer2.put(textureCoordinateData2)
                .position(0);

        int[] textures = new int[1];
        GLES20.glGenTextures(textures.length, textures, 0);
        imageTexture = textures[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imageTexture);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);

    }

    private void initFrameBuffer(int width, int height) {
        //我们先创建一个texture作为和frame buffer的color attachment绑定的texture：
        int[] textures = new int[1];
        GLES20.glGenTextures(textures.length, textures, 0);
        frameBufferTexture = textures[0];

        // 创建frame buffer
        int[] frameBuffers = new int[1];
        GLES20.glGenFramebuffers(frameBuffers.length, frameBuffers, 0);
        frameBuffer = frameBuffers[0];


        //然后将texture与frame buffer的color attachment绑定：
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, frameBufferTexture);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

        /**
         * 然后和绑定frameBufferTexture类似，要对一个frame buffer进行操作，也需要先将它进行绑定，
         * 接下来的glFramebufferTexture2D()就是将frameBufferTexture绑定到frameBuffer的0号attachment上，即GL_COLOR_ATTACHMENT0，
         * 这里大家注意一点，frame buffer有多个color attachment，
         * 但在OpenGL ES 2.0中，只能将texture绑定到0号attachment上，以下是官方API说明对attachment参数的描述：
         */
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, frameBufferTexture, 0);
        //GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);


        int[] textures3 = new int[1];
        GLES20.glGenTextures(1, textures3, 0);
        frameBufferTexture3 = textures3[0];

        int[] frameBuffers3 = new int[1];
        GLES20.glGenFramebuffers(1, frameBuffers3, 0);
        frameBuffer3 = frameBuffers3[0];

        //然后将texture与frame buffer的color attachment绑定：
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, frameBufferTexture3);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer3);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, frameBufferTexture3, 0);

    }


    private int createGLProgram(String vertexShaderCode, String fragmentShaderCode) {
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
        //       Util.checkGLError();
        return programId;
    }


}
