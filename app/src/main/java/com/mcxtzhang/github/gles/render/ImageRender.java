package com.mcxtzhang.github.gles.render;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 如果我们要渲染一张图片，该怎么做呢？这就需要用到纹理，我们需要创建一个纹理并把图片加载到纹理中，然后在fragment shader中对纹理进行采样，从而将纹理渲染出来。
 */
public class ImageRender implements GLSurfaceView.Renderer {
    private static final String TAG = "ImageRender";
    /**
     * 在OpenGL ES 2.0中，attribute就是输入，varying就是从vertex shader往fragment shader的输出
     */
    private String vertexShaderCode =
            "precision mediump float;\n" +
                    "uniform mat4 u_Matrix;\n" +
                    "uniform mat4 u_MatrixTexture;\n" +

                    "attribute vec4 a_Position;\n" +
                    "attribute vec4 a_textureCoordinate;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "void main() {\n" +
                    "    gl_Position = u_Matrix * a_Position;\n" +
                    "    v_textureCoordinate = (u_MatrixTexture * a_textureCoordinate).xy;\n" +
                    "}";


    /**
     * 关键点是uniform sampler2D u_texture;这句，它声明了一个2D的采样器用于采样纹理。
     * <p>
     * varying vec2 v_textureCoordinate;则是从vertex shader中传递过来的一个经过插值的纹理坐标值，关于varying变量
     * gl_FragColor = texture2D(u_texture, v_textureCoordinate);就是从纹理中采样出v_textureCoordinate坐标所对应的颜色作为fragment shader的输出
     * <p>
     * <p>
     * fragment shader的输出实际上就是一个颜色，在之前的文章中，是我们自己去控制这个颜色，而当这个颜色如果是来自纹理的采样，那最终渲染出来就是纹理的样子。
     */
    private String fragmentShaderCode =
            "precision mediump float;\n" +
                    "varying vec2 v_textureCoordinate;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(u_texture, v_textureCoordinate);\n" +
                    "}";


    private final float[] vertexData = new float[]{
            -1f, -1f,
            1f, -1f,

            -1f, 1f,
            1f, 1f,
    };
    //
//    private float[] vertexData = new float[]{
//            -1f*5, -1f*5,
//            -1f*5, 1f*5,
//            1f*5, 1f*5,
//
//            -1f*5, -1f*5,
//            1f*5, 1f*5,
//            1f*5, -1f*5};

    private FloatBuffer mVertexFloatBuffer;
    private int aPositionLocation;

    private final float[] textureCoordinateData = new float[]{
            0f, 1f, 0f, 1f,
            1f, 1f, 0f, 1f,

            0f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
    };
//    private float[] textureCoordinateData = new float[]{
//            0f * 3, 1f * 3,
//            0f * 3, 0f * 3,
//            1f * 3, 0f * 3,
//
//            0f * 3, 1f * 3,
//            1f * 3, 0f * 3,
//            1f * 3, 1f * 3};


    private Bitmap mBitmap;

    private int uMatrixLocation;

    private float[] textureMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };
    private int uMatrixTexLocation;


    public ImageRender(Bitmap bitmap) {
        mBitmap = bitmap;
    }

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


        /**
         * 顶点坐标系的原点在中间。
         */

        mVertexFloatBuffer = ByteBuffer.allocateDirect(vertexData.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexFloatBuffer.put(vertexData);
        mVertexFloatBuffer.position(0);
        // 获取字段a_Position在shader中的位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, "a_Position");
        // 启动对应位置的参数
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        // 指定a_Position所使用的顶点数据
        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT, false, 0, mVertexFloatBuffer);


        /**
         * 纹理坐标的坐标原点在左下角，每个轴的范围是0~1，同样的也可以超出0和1，超出之后的表现会根据设置的纹理参数有所不同。
         */
        FloatBuffer textureBuffer = ByteBuffer.allocateDirect(textureCoordinateData.length * java.lang.Float.SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureBuffer.put(textureCoordinateData)
                .position(0);
        // 获取字段a_Position在shader中的位置
        int a_textureCoordinate = GLES20.glGetAttribLocation(programId, "a_textureCoordinate");
        // 启动对应位置的参数
        GLES20.glEnableVertexAttribArray(a_textureCoordinate);
        // 指定a_Position所使用的顶点数据
        GLES20.glVertexAttribPointer(a_textureCoordinate, 4, GLES20.GL_FLOAT, false, 0, textureBuffer);


        int[] textures = new int[1];
        GLES20.glGenTextures(textures.length, textures, 0);
        int imageTexture = textures[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imageTexture);
        /**
         * 其中GL_TEXTURE_MIN_FILTER和GL_TEXTURE_MAG_FILTER是纹理过滤参数，指定当我们渲染出来的纹理比原来的纹理小或者大时要如何处理，
         * 这里我们使用了GL_LINEAR的方式，这是一种线性插值的方式，得到的结果会更平滑，
         * 除此之外，还有其它很多选项，还有另一个比较常用的是GL_NEAREST，它会选择和它最近的像素，得到的结果锯齿感比GL_LINEAR要大，我们将顶点坐标扩大5倍，即变成-5~5，来得到放大的效果，可以来看看放大时的这两种效果：

         */
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        /**
         * GL_TEXTURE_WRAP_S和GL_TEXTURE_WRAP_T则是指定纹理坐标超出了纹理范围之后，该如何填充，比较常用的有GL_CLAMP_TO_EDGE和GL_REPEAT，
         * 它们的效果分别是填充边缘像素和重复这个纹理，我们将纹理坐标改为0~3，看看效果：
         *
         */
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);


//        ByteBuffer byteBuffer = ByteBuffer.allocate(mBitmap.getWidth() * mBitmap.getHeight() * 4);
//        mBitmap.copyPixelsToBuffer(byteBuffer);
//        byteBuffer.position(0);
//
//
//        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D,
//                0,
//                GLES20.GL_RGBA,
//                mBitmap.getWidth(),
//                mBitmap.getHeight(),
//                0,
//                GLES20.GL_RGBA,
//                GLES20.GL_UNSIGNED_BYTE,
//                byteBuffer);


        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);


        int uTextureLocation = GLES20.glGetAttribLocation(programId, "u_texture");
        //然后给这个location指定对应哪个纹理单元，这里我们使用0号纹理单元：
        GLES20.glUniform1i(uTextureLocation, 0);
        //？是这样的，纹理单元可以想像成是一种类似寄存器的东西，在OpenGL使用纹理前，我们先要把纹理放到某个纹理单元上，之后的操作OpenGL就会去我们指定的纹理单元上去取对应的纹理。


        uMatrixLocation = GLES20.glGetUniformLocation(programId, "u_Matrix");
        uMatrixTexLocation = GLES20.glGetUniformLocation(programId, "u_MatrixTexture");


    }

    private int glSurfaceViewWidth;
    private int glSurfaceViewHeight;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glSurfaceViewWidth = width;
        glSurfaceViewHeight = height;
    }

    private float[] projectionMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };

    private float mCurrentDxInVertex = 0, mCurrentDyInVertex = 0;

    public void translate(float dxInScreen, float dyInScreen) {
        float dxPercent = dxInScreen / glSurfaceViewWidth;
        float dyPercent = dyInScreen / glSurfaceViewHeight;
        float range = (1 - (-1));
        float dxInVertex = dxPercent * range;
        float dyInVertex = -dyPercent * range;


        //偶数x轴，奇数y轴
        for (int i = 0; i < vertexData.length; i++) {
            if (isOdd(i)) {
                vertexData[i] += dyInVertex;
            } else {
                vertexData[i] += dxInVertex;
            }
        }

        mVertexFloatBuffer.put(vertexData);
        mVertexFloatBuffer.position(0);


        //Matrix.translateM(projectionMatrix, 0, dx, dy, 0);
        mCurrentDxInVertex += dxInVertex;
        mCurrentDyInVertex += dyInVertex;
    }

    public static boolean isOdd(int i) {
        return (i & 1) != 0;
    }

    private float mCurrentScale = 1;

    public void scale(float scaleX, float scaleY) {
        Log.d(TAG, "scale() called with: scaleX = [" + scaleX + "], scaleY = [" + scaleY + "]");
        //偶数x轴，奇数y轴
        for (int i = 0; i < vertexData.length; i++) {
            if (isOdd(i)) {
                vertexData[i] = vertexData[i]*scaleX;
            } else {
                vertexData[i] = vertexData[i]* scaleY;
            }
        }

        mVertexFloatBuffer.put(vertexData).position(0);


        mCurrentScale *= scaleX;
    }

    private float mCurrentDegree = 0;

    public void rotate(float targetDegree) {
        float realDegree = targetDegree - mCurrentDegree;
        Log.d(TAG, "rotate() called with: targetDegree = [" + targetDegree + "],realDegree:" + realDegree);

        realDegree *= (float) (Math.PI / 180.0f);

        //Matrix.rotateM(projectionMatrix, 0, realDegree, 0, 0, 1);
        //偶数x轴，奇数y轴
        for (int i = 0; i < vertexData.length; i = i + 2) {
            float x = vertexData[i];
            float y = vertexData[i + 1];
            //new x
            vertexData[i] = (float) (x * Math.cos(realDegree) + y * Math.sin(realDegree));
            //new y
            vertexData[i + 1] = (float) (-x * Math.sin(realDegree) + y * Math.cos(realDegree));
        }
        mVertexFloatBuffer.put(vertexData);
        mVertexFloatBuffer.position(0);


        mCurrentDegree = targetDegree;
        //Matrix.setRotateM(projectionMatrix,0,90,0,0,1);
    }

    public void dump() {
        float m00 = projectionMatrix[0];
        float m01 = projectionMatrix[4];
        float m02 = projectionMatrix[8];

        float m10 = projectionMatrix[1];
        float m11 = projectionMatrix[5];
        float m12 = projectionMatrix[9];

        float m20 = projectionMatrix[2];
        float m21 = projectionMatrix[6];
        float m22 = projectionMatrix[10];

        float m03 = projectionMatrix[12];
        float m13 = projectionMatrix[13];
        float m23 = projectionMatrix[14];

        double scalingFactor = Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);

        double temp = 1.0 / scalingFactor;
        double rotationMatrix = (m00 * temp + m01 * temp + m02 * temp);

        Log.w(TAG, "dump() called, scalingFactor:" + scalingFactor + ",   mCurrentDegree: " + mCurrentDegree + ",rotationMatrix:" + rotationMatrix);
        Log.w(TAG, "dump() called, mCurrentDxInVertex:" + mCurrentDxInVertex + ",   mCurrentDyInVertex: " + mCurrentDyInVertex + ",mCurrentScale:" + mCurrentScale);


        Log.d(TAG, "dump() called, m00:" + m00 + ",   m01: " + m01 + "  ,m02:" + m02);
        Log.d(TAG, "dump() called, m10:" + m10 + ",   m11: " + m11 + "  ,m12:" + m12);
        Log.d(TAG, "dump() called, m20:" + m20 + ",   m21: " + m21 + "  ,m22:" + m22);
        Log.d(TAG, "dump() called, \nm03:" + m03 + "\n,m13: " + m13 + "  \n,m23:" + m23);


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d("TAG", "onDrawFrame() called with: vertexData = [" + vertexData + "]" + ", textureCoordinateData:" + textureCoordinateData);
        GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glViewport(0, 0, glSurfaceViewWidth, glSurfaceViewHeight);


        //Matrix.rotateM(projectionMatrix, 0, 90, 0F, 0F, 1F);

        //Matrix.scaleM(projectionMatrix, 0, 1.5f, 1.5f, 1);
//        Matrix.translateM(projectionMatrix, 0, 0.5f, 0.5f, 0);
//        Matrix.translateM(projectionMatrix, 0, 0.5f, 0.5f, 0);
        //Matrix.translateM(projectionMatrix, 0, 1, 1, 0);

        //平移操作的是顶点坐标
        //Matrix.translateM(projectionMatrix, 0, 1.4f, 0, 0);


        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        GLES20.glUniformMatrix4fv(uMatrixTexLocation, 1, false, textureMatrix, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
