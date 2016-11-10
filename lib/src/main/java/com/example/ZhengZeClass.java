package com.example;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/8/31.
 */
public class ZhengZeClass {



    public static void main(String[] arg) {
/*        AtomicBoolean aBoolean = new AtomicBoolean(false);
        System.out.println("before aBoolean:"+aBoolean);
        if (aBoolean.compareAndSet(false,true)){
            System.out.println("if aBoolean:"+aBoolean);
        }else {
            System.out.println("else aBoolean:"+aBoolean);
        }
        AtomicInteger integer = new AtomicInteger(2);

        System.out.println("before integer:"+integer);
        if (integer.compareAndSet(2,20)){
            System.out.println("if integer:"+integer);
        }else {
            System.out.println("else integer:"+integer);
        }






        Pattern regex1 = Pattern.compile("^(17[0-9]|13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
        Matcher matcher = regex1.matcher("186163203845");
        boolean isMatched = matcher.matches();
        System.out.print("isMatched:"+isMatched);*/

        String s1= new String("上海");
        String s2= new String("a");
        System.out.print("s1:"+s1/*.toUpperCase()*/);
        System.out.print("s2:"+s2.toUpperCase());

    }
}
