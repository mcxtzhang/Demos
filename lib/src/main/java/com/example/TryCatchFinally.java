package com.example;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/3/15.
 * History:
 */

public class TryCatchFinally {

    public static void main(String[] args) {
        System.out.println(getValue1());//2
        System.out.println(getValue2());//1
        System.out.println(getValue3());//1

    }

    static int getValue1() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }

    static int getValue2() {
        int a;
        try {
            a = 1;
            return a;
        } finally {
            a = 2;
            //return a;//返回2
        }
    }

    static int b;

    static int getValue3() {
        try {
            b = 1;
            return b;
        } finally {
            b = 2;
            //return b;//返回2
        }
    }
}
