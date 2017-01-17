package com.mcxtzhang.github;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.TestHelloWorld;
import com.mcxtzhang.HelloWorld;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@TestHelloWorld("haha")
public class TestNewASActivity extends AppCompatActivity {

    private static final String TAG = "zxt/lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        //http://www.cnblogs.com/whoislcj/p/5887859.html

/*        String encodedString = Base64.encodeToString("whoislcj".getBytes(), Base64.DEFAULT);
        Log.e("Base64", "Base64---->" + encodedString);
        String decodedString = new String(Base64.decode(encodedString, Base64.DEFAULT));
        Log.e("Base64", "Base64---->" + decodedString);*/


/*        File file = new File("file:///android_asset/cc.png");
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            String encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
            Log.e("Base64", "Base64---->" + encodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


/*        File desFile = new File("/storage/emulated/0/pimsecure_debug_1.txt");
        FileOutputStream fos = null;
        try {
            byte[] decodeBytes = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
            fos = new FileOutputStream(desFile);
            fos.write(decodeBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //Log.i("TAG", Base64.encodeToString(getFromAssets("cc.png").getBytes(), Base64.DEFAULT));

        //finish();

        HelloWorld.main(null);
        HelloWorld.jump("旋转跳跃");
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");


        HelloWorld.zJump(this, MainActivity.class);
    }


    private static void jump(Context context, Class aClass) {
        Log.d(TAG, "jump() called with: context = [" + context + "], aClass = [" + aClass + "]");
        context.startActivity(new Intent(context, aClass));
    }


    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }


}
