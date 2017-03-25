package com.example;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import static com.example.Key.APP_CLIENT_TYPE;
import static com.example.Key.APP_SIGN_KEY;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/11.
 */

public class Main {
    public interface Generator<T> {
        public T next();
    }

    public static class FruitGenerator implements Generator<String> {


        private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

        @Override
        public String next() {
            Random rand = new Random();
            return fruits[rand.nextInt(3)];
        }
    }

    public static void main(String[] args) {
        Generator<String> generator = new FruitGenerator();
        System.out.println(generator.next());
        System.out.println(generator.next());
        System.out.println(generator.next());
        System.out.println(generator.next());


        System.out.println(isNummber(""));


        System.out.println(~555555555);


        List<String> list = new ArrayList<>();
        System.out.println("list:" + list.size());
        list.add(null);
        list.add(null);
        System.out.println("list:" + list.size());
        System.out.println(list == null);

        List<String> list2 = new ArrayList<>();
        list2.addAll(new ArrayList<String>());
        System.out.println("list2:" + list2.size());
        System.out.println("list2:" + list2.size());


        Vector<String> vector = new Vector<>();
        System.out.println("vector:" + vector.size());
        vector.add(null);
        vector.add(null);
        System.out.println("vector:" + vector.size());
        System.out.println(vector == null);


        Map<String, Object> mapParems = new HashMap<>();
        mapParems.put("type", "android");

        mapParems.put("password", "975421366");
        mapParems.put("mp", "18616320845");

        Map<String, Object> body1 = getBody(mapParems);

        System.out.println(body1);

        try {
            String new1 = URLEncoder.encode(body1.get("data").toString(), "utf-8");
            System.out.println(new1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        System.out.println("2e32" + ((int)2e31));


        System.out.println("String == equlas");

        String str1 = "abc";
        String str2 = "abc";

        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));

        str2 = new String("abc");
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));

        String substring = str2.substring(0, 2);
        System.out.println(substring);
        char[] chars = substring.toCharArray();
        chars[0]='m';
        substring.replace('a','m');
        System.out.println(substring);
        System.out.println(str2);

    }

    public static Map<String, Object> getBody(Map<String, Object> map) {
        String encodeData = toJsonFormMap(map);
        Map<String, Object> tmp = new HashMap<>();
        long time = System.currentTimeMillis() / 1000;
        try {
            encodeData = URLEncoder.encode(encodeData.toString(), "utf-8");
            encodeData = encodeData.replaceAll("\\+", "%20");
            encodeData = URLEncoder.encode(encodeData.toString(), "utf-8");
            encodeData = encodeData.replaceAll("\\+", "%20");

            String appVersion = "3.1.4";
            String deviceId = "862561035025574";
            String sign = MD5.getMD5((appVersion + APP_CLIENT_TYPE + encodeData +
                    deviceId + time + APP_SIGN_KEY).getBytes());
            tmp.put("app_version", appVersion);
            tmp.put("client_type", APP_CLIENT_TYPE + "");
            tmp.put("data", encodeData);
            tmp.put("device_id", deviceId);
            tmp.put("time", time + "");
            tmp.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    protected static final String JSON_BEAN = "jsonBean";

    public static String toJsonFormMap(Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }

        if (map.size() == 1 && map.containsKey(JSON_BEAN)) {
            Object o = map.get(JSON_BEAN);
            return new Gson().toJson(o);
        }
        return new Gson().toJson(map);
    }


    private static int isNummber(String numberString) {
        int k = 1;
        try {
            k = 2;
            return k;
        } finally {
            k = 3;
            return k;
        }
    }
}
