package com.mcxtzhang.github.gles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mcxtzhang.github.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HockeyActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private String TAG = HockeyActivity.class.getSimpleName();


    float[] tableVerticesWithTriangles = {
            // 第一个三角形
            0f, 0f,
            9f, 14f,
            0f, 14f,
            // 第二个三角形
            0f, 0f,
            9f, 0f,
            9f, 14f,
            // 中间的分界线
            0f, 7f,
            9f, 7f,
            // 两个摇杆的质点位置
            4.5f, 2f,
            4.5f, 12f
    };
    private static final int POSITION_COMPONENT_COUNT = 2;


    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hockey);

        glSurfaceView = new GLSurfaceView(this);


        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo deviceConfigurationInfo =
                activityManager.getDeviceConfigurationInfo();
        final boolean supportEs2 = deviceConfigurationInfo.reqGlEsVersion >= 0x20000;

        Toast.makeText(this, "supportEs2:" + supportEs2, Toast.LENGTH_SHORT).show();


        // 指定EGL版本
        glSurfaceView.setEGLContextClientVersion(2);

        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0);


        // 指定渲染器
        //glSurfaceView.setRenderer(new HockeyRenderer(this));
        rendererSet = true;
        glSurfaceView.setRenderer(new HelloWorldRender());


        setContentView(glSurfaceView);


        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }


    public class HockeyRenderer implements GLSurfaceView.Renderer {
        String vertexShaderSource;
        String fragmentShaderSource;


        private static final String U_COLOR = "u_Color";
        private int uColorLocation;
        private static final String A_POSITION = "a_Position";
        private int aPositionLocation;


        public HockeyRenderer(Context context) {

            vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
            fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "onSurfaceCreated() called with: gl = [" + gl + "], config = [" + config + "]");
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

            int programId = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
            GLES20.glUseProgram(programId);

            uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR);
            aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);

            //还记得我们之前已经加载本地内存的桌子顶点数据吗？下一步是要告诉OpenGL到哪里找到属性a_Position对应的数据了。
            //
            //（注意这句话的描述，不是传数据到a_Position，是让OpenGL找。）
            vertexData.position(0);


            GLES20.glEnableVertexAttribArray(aPositionLocation);
            GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);



        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged() called with: gl = [" + gl + "], width = [" + width + "], height = [" + height + "]");
            //GLES20.glViewport(200, 200, width - 200, height - 200);
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d(TAG, "onDrawFrame() called with: gl = [" + gl + "]");

            GLES20.glClearColor(0.9f, 0.9f, 0.9f, 1f);
            GLES20.glClear((GLES20.GL_COLOR_BUFFER_BIT));

            // 设置视口，这里设置为整个GLSurfaceView区域
            // Set the viewport to the full GLSurfaceView


            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);


//
//            GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
//            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
//
//            GLES20.glUniform4f(uColorLocation, 1.0f,0.0f,0.0f,1.0f);
//            GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
//
//
//            GLES20.glUniform4f(uColorLocation, 0.0f,0.0f,1.0f, 1.0f);
//            GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
//            GLES20.glUniform4f(uColorLocation, 0.0f,1.0f,0.0f, 1.0f);
//            GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);

        }
    }
}
