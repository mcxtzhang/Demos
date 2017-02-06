package com.example.classloader;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 测试子类加载父类Classs
 * Created by Administrator on 2017/2/6.
 */

public class ChildLoadParentClassTest {
    public static void main(String[] args) {
        try {
            ClassLoader classLoader = ChildLoadParentClassTest.class.getClassLoader();
            System.out.println("ChildLoadParentClassTest-classLoader:" + classLoader);
            Class a = Class.forName("java.lang.String", true, classLoader);
            System.out.println("a:" + a);


            try {
                String string = (String) a.newInstance();
                System.out.println("string:"+string);
                //能加载出来也能正常用，因为子类会先从父类里找String类
            } catch (Exception e) {
                Logger.getLogger(ClassLoaderTest.class.getName()).log(Level.SEVERE, null, e);
            }


        } catch (ClassNotFoundException e) {

            Logger.getLogger(ClassLoaderTest.class.getName()).log(Level.SEVERE, null, e);

        }


    }
}
