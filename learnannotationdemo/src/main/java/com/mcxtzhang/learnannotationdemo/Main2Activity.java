package com.mcxtzhang.learnannotationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


@FirstAnnotation(value = "这是Activity,是一个类")
public class Main2Activity extends AppCompatActivity {

    @FirstAnnotation(value = "这是一个公开的变量")
    public static final String TAG = "zxt";
    @FirstAnnotation(value = "这是一个保护的变量，var 值为3")
    protected int var = 3;

    @FirstAnnotation(value = "这是oncreate方法，是一个方法")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TestFirstAnnotationBean bean = new TestFirstAnnotationBean("不知道是什么");

        ((TextView) findViewById(R.id.textView)).setText(bean.getExome());


        Class<TestFirstAnnotationBean> testClass = TestFirstAnnotationBean.class;
        Field[] declaredFields = testClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(FirstAnnotation.class) != null) {
                field.setAccessible(true);
                try {
                    Toast.makeText(this, "" + field.getName() + ",  " + field.get(bean), Toast.LENGTH_SHORT).show();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


        //通过反射获取注解的信息
        try {
            Class targetClass = Class.forName("com.mcxtzhang.learnannotationdemo.Main2Activity");
            if (targetClass.isAnnotationPresent(FirstAnnotation.class)) {//如果含有我们的注解
                FirstAnnotation classFirstAnnotation = (FirstAnnotation) targetClass.getAnnotation(FirstAnnotation.class);
                Log.d(TAG, "类有注解 = [" + classFirstAnnotation.value() + "]" + "  " + classFirstAnnotation.isShow());

                //这样应该获取不到oncreate 因为它是protected方法
                for (Method method : targetClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation methodAnnotation = method.getAnnotation(FirstAnnotation.class);
                        Log.d(TAG, "方法有注解 = [" + methodAnnotation.value() + "]   " + methodAnnotation.isShow());
                    }
                }


                for (Field field : targetClass.getFields()) {
                    if (field.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation varAnnotation = field.getAnnotation(FirstAnnotation.class);
                        Log.i(TAG, "公开变量方法有注解 = [" + varAnnotation.value() + "]   " + varAnnotation.isShow());
                    }
                }


                for (Field field : targetClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation varAnnotation = field.getAnnotation(FirstAnnotation.class);
                        Log.i(TAG, "所有变量方法有注解 = [" + varAnnotation.value() + "]   " + varAnnotation.isShow());
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FirstAnnotation(value = "测试私有方法是否可见")
    private void testAnnotationMethod() {

    }

    @FirstAnnotation(value = "测试私有变量是否可见", isShow = false)
    private String testAnnotationVar1;
}
