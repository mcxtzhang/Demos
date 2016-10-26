package com.mcxtzhang.zxtcommonlib;

import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.TouchDelegate;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 介绍：乱七八糟的Utils
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/1.
 */
public class ZxtUtils {

    /**
     * 返回变成朋友蓝色的SpanString
     *
     * @param source 源数据
     * @param key    要匹配的key
     * @return
     */
    public static SpannableString getFriendColorSpannableString(String source, String key) {
        return getColorSpannableString(source, key, Color.parseColor("#4C9AD2"));
    }

    /**
     * 返回变色的SpanString
     *
     * @param source 源数据
     * @param key    要匹配的key
     * @param color  要变的色
     * @return
     */
    public static SpannableString getColorSpannableString(String source, String key, int color) {
        if (source == null) {
            source = "";
        }
        SpannableString spannableString = new SpannableString(source);//构建Span
        if (!TextUtils.isEmpty(key)) {
            for (int i = 0; i < key.length(); i++) {
                int indexB = source.indexOf(key.charAt(i));
                if (indexB > -1) {//匹配到了,加一个spans
                    spannableString.setSpan(/*span*/new ForegroundColorSpan(color), indexB, indexB + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return spannableString;
    }

    /**
     * 返回商品的name，如果是活动，增加 活动tag字样，如果不是，不增加
     *
     * @param source
     * @param isAct
     * @return
     */
    public static SpannableString getBfGoodsNameIsActSpannableString(String source, boolean isAct) {
        SpannableString spannableString;
        if (isAct) {
            String tag = " 活动 ";//tag字样
            source = tag + source;
            spannableString = new SpannableString(source);
            spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#ffdc5b")), 0, tag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置背景色
            spannableString.setSpan(new AbsoluteSizeSpan(12, true), 0, tag.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//改变部分文字大小
        } else {
            spannableString = new SpannableString(source);
        }
        return spannableString;
    }


    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNum(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern regex1 = Pattern.compile("^(17[0-9]|13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
        Matcher matcher = regex1.matcher(phone);
        return matcher.matches();//是精准搜索
    }


    /**
     * 判断是否是EMail
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        Pattern regex1 = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = regex1.matcher(email);
        return matcher.matches();

    }

    /**
     * 解码URL 去掉特殊字符
     * http://starry.imcoming.com.cn/sign.html?type=1022&params%5Burl%5D=http%3A%2F%2Fstarry.imcoming.com.cn%2Fsign.html%3Fkey%3D123123213123%26user_id%3D123&params%5Btitle%5D=%E7%AD%BE%E5%88%B0&params%5BisNeedLogin%5D=1
     * http://starry.imcoming.com.cn/sign.html?type=1022&params[url]=http://starry.imcoming.com.cn/sign.html?key=123123213123&user_id=123&params[title]=签到&params[isNeedLogin]=1
     *
     * @param url
     * @return
     */
    public static String getDecodeUrl(String url) {
        url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 从字符串里返回第一个数字串
     *
     * @param text
     * @return
     */
    public static String getNumberFromString(String text) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return (m.group(1));
        }
        return "";
    }

    /**
     * 将long转化为"yyyy.MM.dd"
     *
     * @param dateLong
     * @return
     */
    public static String long2String1(long dateLong) {
        if (dateLong == -1) {
            return "至今";
        }
        return new SimpleDateFormat("yyyy.MM.dd").format(dateLong);
    }

    /**
     * 将long转化为"MM.dd"
     *
     * @param dateLong
     * @return
     */
    public static String long2String2(long dateLong) {
        if (dateLong == -1) {
            return "至今";
        }
        return new SimpleDateFormat("MM.dd").format(dateLong);
    }


    /**
     * 为View扩大点击范围
     *
     * @param view
     * @param expandTouchWidth
     */
    public static void expandViewClickRect(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect);
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;
                TouchDelegate touchDelegate = new TouchDelegate(rect, view);
                parentView.setTouchDelegate(touchDelegate);
            }
        });
    }

}
