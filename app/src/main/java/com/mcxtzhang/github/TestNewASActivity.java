package com.mcxtzhang.github;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DIActivity;
import com.example.DIView;
import com.example.TestHelloWorld;
import com.mcxtzhang.HelloWorld;
import com.mcxzhang.ZRouter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@DIActivity
@TestHelloWorld("haha")
public class TestNewASActivity extends AppCompatActivity {

    private static final String TAG = "zxt/lifecycle";

    @DIView(R.id.editText)
    EditText mEt;

    @DIView(R.id.tv)
    TextView mTv;

    @DIView(R.id.root)
    LinearLayout mRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "A onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        //finish();
        //ZRouter.getInstance().jump(TestNewASActivity.this, "touch", null, 101);


        ZBindTestNewASActivity.bindView(this);

        findViewById(R.id.cstView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.invalidate();
                view.requestLayout();
            }
        });

        mRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick() called with: root = [" + v + "]");
                return false;
            }
        });

        findViewById(R.id.btnSparseArray).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick() called with: inner = [" + v + "]");
                return false;
            }
        });

        findViewById(R.id.btnSparseArray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouter.getInstance().jump(TestNewASActivity.this, /*SparseArrayOrderTestActivity.TAG*/"testView", null);
            }
        });

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


        mEt.setText("绑定成功");
        mTv.setText("绑定成功");
        mTv.setSelected(true);


        HelloWorld.main(null);
        HelloWorld.jump("旋转跳跃");


        //RManager.getInstance().jump(this,"router1");

        ///new ZRouter().jump(this,"second");


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouter.getInstance().jump(TestNewASActivity.this, "main", null, 100);

            }
        });

        findViewById(R.id.tv).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key-string", "jump params in bundle");

                //ZRouter.getInstance().jump(TestNewASActivity.this, "rx", bundle, 101);
                ZRouter.getInstance().jump(TestNewASActivity.this, "touch", bundle, 101);

                //RManager2.getInstance().jump(TestNewASActivity.this, "rx", bundle);
                return true;
            }
        });


        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = (ImageView) view;
                Log.d(TAG, "onClick() called with: getWidth = [" + image.getWidth() + "]");
                Log.d(TAG, "onClick() called with: getHeight= [" + image.getHeight() + "]");
                Log.d(TAG, "onClick() called with: getWidth = [" + image.getDrawable().getIntrinsicHeight() + "]");
                Log.d(TAG, "onClick() called with: getHeight= [" + image.getDrawable().getIntrinsicWidth() + "]");
            }
        });

        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = (ImageView) view;
                Log.d(TAG, "onClick() called with: getWidth = [" + image.getWidth() + "]");
                Log.d(TAG, "onClick() called with: getHeight = [" + image.getHeight() + "]");
                Log.d(TAG, "onClick() called with: getWidth = [" + image.getDrawable().getIntrinsicWidth() + "]");
                Log.d(TAG, "onClick() called with: getHeight = [" + image.getDrawable().getIntrinsicHeight() + "]");

            }
        });

        final List<String> list = new ArrayList<String>();
        final Vector<String> vector = new Vector<String>();
        for (int i = 0; i < 1000000; i++) {
            list.add("a");
            vector.add("b");
        }
        findViewById(R.id.btnTestForTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                long time1 = System.currentTimeMillis();
                for (String s : list) {
                    String temp = s;
                    //System.out.println(s);
                }
                long time2 = System.currentTimeMillis();
                Log.e(TAG, "List 增强for-each循环 耗时:" + (time2 - time1));
                for (String s : vector) {
                    String temp = s;
                    //System.out.println(s);
                }
                long time3 = System.currentTimeMillis();
                Log.e(TAG, "Vector 增强for-each循环耗时:" + (time3 - time2));
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i);
                    //System.out.println(list.get(i));
                }
                long time4 = System.currentTimeMillis();
                Log.e(TAG, "List index for循环 耗时:" + (time4 - time3));


                for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
                    //System.out.println(iterator.next());
                    String temp = iterator.next();
                }
                long time5 = System.currentTimeMillis();
                Log.e(TAG, "List Iterator for循环 耗时:" + (time5 - time4));


            }
        });
    }


    private static void jump(Context context, Class aClass) {
        Log.d(TAG, "jump() called with: context = [" + context);
        context.startActivity(new Intent(context, aClass));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //finish();
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        Toast.makeText(this, "resultCode:" + resultCode + ",requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
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
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
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
        Log.d(TAG, "A onDestroy() called");
        super.onDestroy();
    }


}
