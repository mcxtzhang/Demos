package com.mcxtzhang.zhiwendemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/6/15.
 * History:
 */

public class FingerLoginUtils {
    private static final String SP_NAME = "aly_f_helper";

    //开关Key后缀
    private static final String KEY_SUFFIX_SWITCH = "KEY_SUFFIX_SWITCH";
    //pwdKey 后缀
    private static final String KEY_SUFFIX_P = "KEY_SUFFIX_P";
    //iv key 后缀
    private static final String KEY_SUFFIX_IV = "KEY_SUFFIX_IV";

    /**
     * 存P和IV。
     * 根据P 加密，保存加密后的P 和 IV。
     *
     * @param context
     * @param account
     * @param pwd
     */
    public static void savePwd(Context context, String account, String pwd) {
        try {
            Cipher cipher = FingerUtils.getCipher();
            byte[] encrypted = cipher.doFinal(pwd.getBytes());
            byte[] IV = cipher.getIV();
            String se = Base64.encodeToString(encrypted, Base64.URL_SAFE);
            String siv = Base64.encodeToString(IV, Base64.URL_SAFE);
            SPUtils.put(context, SP_NAME, account + KEY_SUFFIX_P, se);
            SPUtils.put(context, SP_NAME, account + KEY_SUFFIX_IV, siv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getPwd(Context context, String account) {
        String se = (String) SPUtils.get(context, SP_NAME, account + KEY_SUFFIX_P, "");
        if (TextUtils.isEmpty(se))
            return null;
        return Base64.decode(se, Base64.URL_SAFE);
    }

    public static byte[] getIv(Context context, String account) {
        String se = (String) SPUtils.get(context, SP_NAME, account + KEY_SUFFIX_IV, "");
        if (TextUtils.isEmpty(se))
            return null;
        return Base64.decode(se, Base64.URL_SAFE);
    }

    /**
     * 是否保存过该账号的密码
     *
     * @param context
     * @param account
     * @return
     */
    public static boolean hasSetPwd(Context context, String account) {
        byte[] pwd = getPwd(context, account);
        byte[] iv = getIv(context, account);
        if (pwd == null || iv == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 指纹登录开关状态
     *
     * @param context
     * @return
     */
    public static boolean getFingerLoginSwitch(Context context, String account) {
        //默认开
        return (boolean) SPUtils.get(context, SP_NAME, account + KEY_SUFFIX_SWITCH, true);
    }

    public static void setFingerLoginSwitch(Context context, String account, boolean open) {
        SPUtils.put(context, SP_NAME, account + KEY_SUFFIX_SWITCH, open);
    }

}
