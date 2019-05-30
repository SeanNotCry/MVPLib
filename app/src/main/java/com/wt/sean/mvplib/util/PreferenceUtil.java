package com.wt.sean.mvplib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preferences util.
 *
 * @author markmjw
 * @date 2014-03-13
 */
public class PreferenceUtil {
    private static final String PREF_NAME = "client_pref";

    private static SharedPreferences sPref;

    public static void init(Context context) {
        sPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 删除某值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 保存Long型值
     *
     * @param key   关键字
     * @param value 值
     */
    public static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取指定关键字对应的Long型值
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return key对应的value
     */
    public static long getLong(String key, long defaultValue) {
        return sPref.getLong(key, defaultValue);
    }

    /**
     * 获取指定关键字对应的Long型值
     *
     * @param key 关键字
     * @return key对应的value，默认-1
     */
    public static long getLong(String key) {
        return sPref.getLong(key, -1);
    }

    /**
     * 保存Float型值
     *
     * @param key   关键字
     * @param value 值
     */
    public static void saveFloat(String key, float value) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取指定关键字对应的Float型值
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return key对应的value
     */
    public static float getFloat(String key, float defaultValue) {
        return sPref.getFloat(key, defaultValue);
    }

    /**
     * 获取指定关键字对应的Float型值
     *
     * @param key 关键字
     * @return key对应的value，默认-1
     */
    public static float getFloat(String key) {
        return sPref.getFloat(key, -1.0f);
    }



    /**
     * 保存Int型值
     *
     * @param key   关键字
     * @param value 值
     */
    public static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取指定关键字对应的Int型值
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return key对应的value
     */
    public static int getInt(String key, int defaultValue) {
        return sPref.getInt(key, defaultValue);
    }

    /**
     * 获取指定关键字对应的Int型值
     *
     * @param key 关键字
     * @return key对应的value，默认-1
     */
    public static int getInt(String key) {
        return sPref.getInt(key, -1);
    }

    /**
     * 保存指定Key对应的String
     *
     * @param key   关键字
     * @param value 值
     */
    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取指定Key对应的字符串值,有默认值
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return key对应的value
     */
    public static String getString(String key, String defaultValue) {
        return sPref.getString(key, defaultValue);
    }

    /**
     * 获取指定Key对应的String值
     *
     * @param key 关键字
     * @return key对应的value，默认为空字符串""
     */
    public static String getString(String key) {
        return sPref.getString(key, "");
    }

    /**
     * 保存指定Key对应的Boolean值
     *
     * @param key   关键字
     * @param value 值
     */
    public static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取指定Key对应的boolean值
     *
     * @param key          关键字
     * @param defaultValue 默认值
     * @return key对应的value
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return sPref.getBoolean(key, defaultValue);
    }

    /**
     * 获取指定Key对应的boolean值
     *
     * @param key 关键字
     * @return key对应的value, 默认为false
     */
    public static boolean getBoolean(String key) {
        return sPref.getBoolean(key, false);
    }

    public static boolean containKey(String key) {
        return sPref.contains(key);
    }

}
