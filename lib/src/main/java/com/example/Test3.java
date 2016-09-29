package com.example;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String ss = "type=1022&params%5Burl%5D=http%3A%2F%2Fstarry.imcoming.com.cn%2Fsign.html%3Fkey%3D123123213123%26user_id%3D123&params%5Btitle%5D=%E7%AD%BE%E5%88%B0&params%5BisNeedLogin%5D=1";


/*        ss = ss.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            String urlStr = URLDecoder.decode(ss, "UTF-8");
            System.out.println(urlStr);
            System.out.println(getNumberFromString(urlStr));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        System.out.println(getDecodeUrl(ss));

        String decodeUrl = getDecodeUrl(ss);
        String keyURl = "&params[url]=";
        String keyTitle = "&params[title]=";
        String keyLogin = "&params[isNeedLogin]=";

        int urlIndex = decodeUrl.indexOf(keyURl);
        int titleIndex = decodeUrl.indexOf(keyTitle);
        int isNeedLoginIndex = decodeUrl.indexOf(keyLogin);

        String url = decodeUrl.substring(urlIndex + keyURl.length(), titleIndex);
        String title = decodeUrl.substring(titleIndex + keyTitle.length(), isNeedLoginIndex);
        String isNeedLogin = decodeUrl.substring(isNeedLoginIndex + keyLogin.length());

        System.out.println(url);
        System.out.println(title);
        System.out.println(isNeedLogin);
    }


    public static String getDecodeUrl(String url) {
        url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getNumberFromString(String text) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return (m.group(1));
        }
        return "";
    }

}