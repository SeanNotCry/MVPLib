package com.wt.sean.mvplib.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;

/**
 * 文本工具类
 * <p/>
 * Created by MaXingliang on 2014/10/9.
 */
public class TextUtil {


    /**
     * 加密签名
     *
     * @param strSrc  要加密的字符串
     * @param encName 加密类型
     * @return 签名值
     */
    public static String encrypt(String strSrc, String encName) {
        String sign = "";
        try {
            MessageDigest md = MessageDigest.getInstance(encName);
            sign = byte2hex(md.digest(strSrc.getBytes("utf-8")));
        } catch (Exception e) {
            // ignore
        }

        return sign;
    }

    /**
     * 二行制转字符串
     *
     * @param bytes
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String temp;
        for (byte b : bytes) {
            temp = (Integer.toHexString(b & 0XFF));
            if (temp.length() == 1) builder.append("0").append(temp);
            else builder.append(temp);
        }
        return builder.toString().toLowerCase(Locale.CHINA);
    }

    /**
     * 生成随机数字和字母
     *
     * @param length 参数length，表示生成几位随机数
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 高亮文本
     *
     * @param text  文本内容
     * @param color 高亮颜色
     * @param start 高亮开始位置
     * @param end   高亮结束位置
     * @return
     */
    public static SpannableStringBuilder highLight(CharSequence text, int color, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    public static String getUrlType(String url){
        String type="";
        if(TextUtils.isEmpty(url)){
            return type;
        }else {
            int a=url.lastIndexOf(".");
            if(a!=-1){
                type=url.substring(a,url.length());
            }
        }
        return type;
    }

    public static String getFileNameNoHouZhu(String finame){
        String name="";
        if(TextUtils.isEmpty(finame)){
            return "";
        }else {
            int a=finame.lastIndexOf(".");
            name=finame.substring(0,a);
            if(name.indexOf(".")!=-1){
                //去除剩余的 .
                name=name.replace(".","");
            }
        }
        return name;
    }
}
