package com.mcxtzhang.github;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DIActivity;
import com.example.DIView;
import com.example.TestHelloWorld;
import com.mcxtzhang.HelloWorld;
import com.mcxtzhang.github.kotlin.FirstBean;
import com.mcxtzhang.github.kotlin.ThridBean;
import com.mcxtzhang.github.routerexample.RManager2;
import com.mcxtzhang.zxtcommonlib.widget.dialog.DialogManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@DIActivity
@TestHelloWorld("haha")
public class TestNewASActivity extends AppCompatActivity {
    Context mContext;

    private static final String TAG = "zxt/lifecycle";

    @DIView(R.id.editText)
    EditText mEt;

    @DIView(R.id.tv)
    TextView mTv;

    @DIView(R.id.root)
    LinearLayout mRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = TestNewASActivity.this;
        Log.d(TAG, "A onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);

        ClassLoader classLoader = TestNewASActivity.class.getClassLoader();

        while (classLoader != null) {
            Log.d(TAG, "onCreate() called with: classLoader = [" + classLoader + "]");
            classLoader = classLoader.getParent();
        }


        setContentView(R.layout.test);
        //finish();
        //ZRouter.getInstance().jump(TestNewASActivity.this, "touch", null, 101);


        ZBindTestNewASActivity.bindView(this);

        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TestNewASActivity.this, "如果我显示出来了 那就是泄漏了", Toast.LENGTH_SHORT).show();
            }
        }, 2000);

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
                //ZRouter.getInstance().jump(TestNewASActivity.this, /*SparseArrayOrderTestActivity.TAG*/"firstKotlin", null);
                RManager2.getInstance().jump(TestNewASActivity.this, "firstKotlin", null);
            }
        });

        List<FirstBean> kotlinBeanList = new ArrayList<>();

        FirstBean firstBean = new FirstBean("zhangxutong");
        kotlinBeanList.add(firstBean);

        kotlinBeanList.add(firstBean);

        ThridBean thridBean = new ThridBean();
        thridBean.setAge(1).setName("z").setPrice(3.54);


        Toast.makeText(this, "aaa:" + thridBean, Toast.LENGTH_SHORT).show();


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

        ViewGroup.LayoutParams layoutParams = mEt.getLayoutParams();
        layoutParams.height = 300;
        mEt.setLayoutParams(layoutParams);

        //mEt.setText("绑定成功");
        mTv.setText("绑定成功");
        mTv.setSelected(true);

        mTv.setText("我牛逼\n你牛逼");


        mTv.setText(getString(R.string.no, "这道菜"));


        HelloWorld.main(null);
        HelloWorld.jump("旋转跳跃");


        //RManager.getInstance().jump(this,"router1");

        ///new ZRouter().jump(this,"second");


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ZRouter.getInstance().jump(TestNewASActivity.this, "main", null, 100);
                TextView textView = new TextView(mContext);
                textView.setText("启用自动打印功能，需要先连接打印机，请调试打印机设置并确保连接成功。");
                DialogManager.showCustom(mContext, textView
                        , "知道了");


/*                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();*/ //RING、 SYSTEM

                //MUSIC
                MediaPlayer mp = MediaPlayer.create(mContext, R.raw.msg_hint);
                mp.start();

            }
        });

        findViewById(R.id.tv).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
/*                Bundle bundle = new Bundle();
                bundle.putString("key-string", "jump params in bundle");

                //ZRouter.getInstance().jump(TestNewASActivity.this, "rx", bundle, 101);
                ZRouter.getInstance().jump(TestNewASActivity.this, "touch", bundle, 101);

                //RManager2.getInstance().jump(TestNewASActivity.this, "rx", bundle);*/


/*                View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_upgrade, null);
                ((TextView) inflate.findViewById(R.id.tvTitle)).setText("检测到新版本：8.8.8");
                ((TextView) inflate.findViewById(R.id.tvContent)).setText("优化菜品管理\n" +
                        "取消订单流程优化\n" +
                        "增加无网络提示\n" +
                        "欢迎下载体验～");
                DialogManager.showCustom(mContext, inflate
                        , "稍后再说", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(TestNewASActivity.this, " 啊哈哈哈", Toast.LENGTH_SHORT).show();
                            }
                        }, "立即更新", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(TestNewASActivity.this, " 啊 哈", Toast.LENGTH_SHORT).show();
                            }
                        });*/


                AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

//通话音量

                int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
                int current = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
                Log.d("VIOCE_CALL", "max : " + max + " current : " + current);

//系统音量

                max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
                current = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                Log.d("SYSTEM", "max : " + max + " current : " + current);

//铃声音量

                max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                current = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
                Log.d("RING", "max : " + max + " current : " + current);

//音乐音量（媒体音量的设置）

                max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Log.d("MUSIC", "max : " + max + " current : " + current);

//提示声音音量

                max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                current = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
                Log.d("ALARM", "max : " + max + " current : " + current);
                return true;
            }
        });


        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView image = (ImageView) view;
/*                Log.d(TAG, "onClick() called with: getWidth = [" + image.getWidth() + "]");
                Log.d(TAG, "onClick() called with: getHeight= [" + image.getHeight() + "]");
                Log.d(TAG, "onClick() called with: getWidth = [" + image.getDrawable().getIntrinsicHeight() + "]");
                Log.d(TAG, "onClick() called with: getHeight= [" + image.getDrawable().getIntrinsicWidth() + "]");*/

                Log.d(TAG, "onClick() called with: view = [" + view.getRotationY() + "]");

                image.setRotationY(0);
                //一圈0.5s 转两圈
                final ObjectAnimator rotationY = ObjectAnimator.ofFloat(image, "rotationY", 720);
                rotationY.setDuration(1000);
                rotationY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        image.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rotationY.start();
                            }
                        }, 1000);
                    }
                });
                rotationY.start();


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
        findViewById(R.id.btnTestForTimeForEach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time1 = System.currentTimeMillis();
                for (String s : list) {
                    String temp = s;
                    System.out.println(temp);
                }
                long time2 = System.currentTimeMillis();
                Log.e(TAG, "List 增强for-each循环 耗时:" + (time2 - time1));
            }
        });

        findViewById(R.id.btnTestForTimeIterator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time4 = System.currentTimeMillis();
                for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
                    //System.out.println(iterator.next());
                    String temp = iterator.next();
                    System.out.println(temp);
                }
                long time5 = System.currentTimeMillis();
                Log.e(TAG, "List Iterator for循环 耗时:" + (time5 - time4));
            }

        });


        findViewById(R.id.btnTestForTimeNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time3 = System.currentTimeMillis();
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i);
                    System.out.println(temp);
                }
                long time4 = System.currentTimeMillis();
                Log.e(TAG, "List index for循环 耗时:" + (time4 - time3));
            }
        });


        Map<String, String> map = new ArrayMap<>();
        map.put("1", "1");
        map.put(null, "2");
        map.put("3", null);
        map.put("6", null);
        map.put("5", null);
        map.put("4", null);
        Log.e("TAG", "onCreate() called with: ArrayMap = [" + map + "]");


        SparseArray<String> stringSparseArray = new SparseArray<>();
        stringSparseArray.put(1, "a");
        stringSparseArray.put(5, "e");
        stringSparseArray.put(4, "d");
        stringSparseArray.put(10, "h");
        stringSparseArray.put(2, null);

        Log.d(TAG, "onCreate() called with: stringSparseArray = [" + stringSparseArray + "]");


        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TestNewASActivity.this, RxActivity.class);
                startActivity(intent);
            }
        }, 3000);


        //new PriorityThread().start();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Thread.currentThread().getPriority() = [" + Thread.currentThread().getPriority() + "]");
                Log.d(TAG, "Process.getThreadPriority() = [" + Process.getThreadPriority((int) Thread.currentThread().getId()) + "]");
                Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);
                Log.d(TAG, "222 Process.getThreadPriority() = [" + Process.getThreadPriority((int) Thread.currentThread().getId()) + "]");
                Log.d(TAG, "run() called:" + this);
            }
        }).start();*/


        //得到合适宽度：
        Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
        Paint paint = new Paint();
        //传入像素
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, metrics));

        String toBeMeasure = "我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高我多宽多高";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = " ";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: toBeMeasure.length() = [" + toBeMeasure.length() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = "\n";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = "\t";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = "哎";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = "哎哎";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");

        toBeMeasure = "哎 哎";
        paint.getTextBounds(toBeMeasure, 0, toBeMeasure.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
        Log.d(TAG, "onCreate() called with: indexBounds.height() = [" + indexBounds.height() + "]");
        Log.d(TAG, "onCreate() called with: indexBounds.width() = [" + indexBounds.width() + "]");




        EditText etSpan1 = (EditText) findViewById(R.id.etSpan1);
        SpannableStringBuilder ssb = new SpannableStringBuilder("哈哈哈1");
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan1.setHint(ssb);


        EditText etSpan2 = (EditText) findViewById(R.id.etSpan2);
        ssb = new SpannableStringBuilder("哈哈哈2");
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan2.setHint(ssb);

        EditText etSpan3 = (EditText) findViewById(R.id.etSpan3);
        ssb = new SpannableStringBuilder("哈哈哈3");
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan3.setHint(ssb);

       /* EditText etSpan4 = (EditText) findViewById(R.id.etSpan2);
        ssb = new SpannableStringBuilder("哈哈哈");
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_LEFT), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan2.setHint(ssb);

        EditText etSpan5 = (EditText) findViewById(R.id.etSpan2);
        ssb = new SpannableStringBuilder("哈哈哈");
        ssb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_RIGHT), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etSpan2.setHint(ssb);*/


    }

    private static class PriorityThread extends Thread {

        public PriorityThread() {

        }

        @Override
        public void run() {
            super.run();

        }
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
