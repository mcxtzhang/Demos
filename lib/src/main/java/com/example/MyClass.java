package com.example;

import com.example.bean.JobInfoBean;
import com.google.gson.Gson;

public class MyClass {
    private static final int MODE_SHIFT = 30;
    private static final int MODE_MASK = 0x3 << MODE_SHIFT;

    /**
     * Measure specification mode: The parent has not imposed any constraint
     * on the child. It can be whatever size it wants.
     */
    public static final int UNSPECIFIED = 0 << MODE_SHIFT;

    /**
     * Measure specification mode: The parent has determined an exact size
     * for the child. The child is going to be given those bounds regardless
     * of how big it wants to be.
     */
    public static final int EXACTLY = 1 << MODE_SHIFT;

    /**
     * Measure specification mode: The child can be as large as it wants up
     * to the specified size.
     */
    public static final int AT_MOST = 2 << MODE_SHIFT;

    public static void main(String[] args) {
/*        System.out.println(MODE_MASK);
        System.out.println(UNSPECIFIED);
        System.out.println(EXACTLY);
        System.out.println(AT_MOST);


        System.out.println(0 + 1234);
        System.out.println(0 | 1234);


        Integer a = 1;
        Integer b = 1;

        Integer c = new Integer(1);
        Integer d = new Integer(1);


        System.out.println((a==b)+"????");
        System.out.println((c.equals(d))+"????");


        System.out.println("".compareTo("anhui"));


        List<String> localCityNameList = null;
        for (String s : localCityNameList) {
            System.out.println(s);
        }*/

        String a = null;
        try {
            if (a.equals("")) {

            }
        } catch (Exception e) {
            System.out.println("error");
            return;
        }


        System.out.println(new Gson().toJson(new JobInfoBean()));





    }
}
