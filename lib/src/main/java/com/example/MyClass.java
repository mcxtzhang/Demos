package com.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

/*        String a = null;
        try {
            if (a.equals("")) {

            }
        } catch (Exception e) {
            System.out.println("error");
            return;
        }


        System.out.println(new Gson().toJson(new JobInfoBean()));*/

        List list = initDatas();
        System.out.println("list:" + list.size());
/*        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                list.remove(i);
            }
        }*/
        Iterator iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            if (i % 2 == 0) {
                iterator.remove();
            }
            i++;
        }

        System.out.println("list:" + list.size());






        String city = "我爱祖国天安门";
        city = city.substring(1,4)+"...";
        System.out.println(city);
    }


    public static List initDatas() {
        List datas = new ArrayList<>();
        datas.add(new String("http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg"));
        datas.add(new String("http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg"));
        datas.add(new String("http://news.k618.cn/tech/201604/W020160407281077548026.jpg"));
        datas.add(new String("http://www.kejik.com/image/1460343965520.jpg"));
        datas.add(new String("http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg"));
        datas.add(new String("http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg"));
        datas.add(new String("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg"));
        return datas;
    }
}
