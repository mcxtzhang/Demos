package com.example;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

@FirstAnnotation(value = "这是MyClass,是一个类")
public class MyClass {

    @FirstAnnotation(value = "这是一个公开的变量")
    public static final String TAG = "zxt";
    @FirstAnnotation(value = "这是一个保护的变量，var 值为3")
    protected int var = 3;
    @FirstAnnotation(value = "测试私有变量是否可见", isShow = false)
    private String testAnnotationVar1;


    @FirstAnnotation(value = "测试私有方法是否可见")
    private void testAnnotationMethod() {

    }

    @FirstAnnotation(value = "main，是一个方法")
    public static void main(String[] args) {
        TestFirstAnnotationBean bean = new TestFirstAnnotationBean("不知道是什么");

        Class<TestFirstAnnotationBean> testClass = (Class<TestFirstAnnotationBean>) bean.getClass();
        Field[] declaredFields = testClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(FirstAnnotation.class) != null) {
                field.setAccessible(true);
                try {
                    System.out.println("" + field.getName() + ",  " + field.get(bean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


        //通过反射获取注解的信息
        try {
            MyClass myClass = new MyClass();
            Class targetClass = /*Class.forName("com.example.MyClass")*/myClass.getClass();
            if (targetClass.isAnnotationPresent(FirstAnnotation.class)) {//如果含有我们的注解
                FirstAnnotation classFirstAnnotation = (FirstAnnotation) targetClass.getAnnotation(FirstAnnotation.class);
                System.out.println("类有注解 = [" + classFirstAnnotation.value() + "]" + "  " + classFirstAnnotation.isShow());

                //这样应该获取不到oncreate 因为它是protected方法
                for (Method method : targetClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation methodAnnotation = method.getAnnotation(FirstAnnotation.class);
                        System.out.println("方法有注解 = [" + methodAnnotation.value() + "]   " + methodAnnotation.isShow());
                    }
                }


                for (Field field : targetClass.getFields()) {
                    if (field.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation varAnnotation = field.getAnnotation(FirstAnnotation.class);
                        try {
                            System.out.println("公开变量有注解 = [" + varAnnotation.value() + "]   " + varAnnotation.isShow() + " ，值为：" + field.get(myClass));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }


                for (Field field : targetClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(FirstAnnotation.class)) {
                        FirstAnnotation varAnnotation = field.getAnnotation(FirstAnnotation.class);
                        System.out.println("所有变量有注解 = [" + varAnnotation.value() + "]   " + varAnnotation.isShow());
                        try {
                            System.out.println("他的名：" +field.getName()+",值"+ field.get(myClass));
                            if ("testAnnotationVar1".equals(field.getName())){
                                field.set(myClass,"你看着办");
                                System.out.println("他被我改了：" +field.getName()+",值"+ field.get(myClass));
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
