package com.example;

import java.security.MessageDigest;

/**
 * @author 周新宇
 * @version V1.0
 * @ClassName: zhouxinyu-pc
 * @Description: ${todo}(MD5)
 * @date 15/9/7 下午3:34
 */
public class MD5 {
    /*public static String getMD5(String date) {

        return md5(getToMd5Str(date));
    }
    
    public static String getImgMD5(String date) {

        return md5(getImgUploadToMd5Str(date));
    }*/
    public static String getMD5(byte[]  date) {

        return md5(date);
    }
   /* public static String md5(String str) {

        byte[] hash;
        try {

            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {


            throw new RuntimeException("Huh, MD5 should be supported?", e);

        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("Huh, UTF-8 should be supported?", e);

        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {

            if ((b & 0xFF) < 0x10) hex.append("0");

            hex.append(Integer.toHexString(b & 0xFF));

        }

        return hex.toString().toUpperCase();

    }*/
   public static String md5(String source) {
       return md5(source.getBytes());
   }

    public static String md5(byte[] source) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;

            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成md5
     * @param s
     * @return
     */
    public static String md5lal(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   /* public static String getToMd5Str(String date) {
        StringBuffer mStr = new StringBuffer();
        mStr.append(Key.KEY_STORE);
        mStr.append("apiVersion");
        mStr.append(Key.KEY_VERSION);
        mStr.append("appKey");
        mStr.append(Key.APPKEY);
        mStr.append("timeStamp");
        mStr.append(date);
        return mStr.toString();

    }*/

}
