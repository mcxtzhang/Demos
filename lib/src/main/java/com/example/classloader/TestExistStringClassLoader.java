package com.example.classloader;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/6.
 * History:
 */

public class TestExistStringClassLoader {
    public static void main(String[] args){
        System.out.println("String.class.getClassLoader();"+String.class.getClassLoader());
        System.out.println("int.class.getClassLoader();"+int.class.getClassLoader());

        System.out.println("Boolean.class.getClassLoader();"+Boolean.class.getClassLoader());

        System.out.println("char.class.getClassLoader();"+char.class.getClassLoader());
        System.out.println("Character.class.getClassLoader();"+Character.class.getClassLoader());

        System.out.println("TestNotNullClass.class.getClassLoader():"+TestNotNullClass.class.getClassLoader());
    }
}
