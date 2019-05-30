package com.wt.sean.mvplib.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class ToastUtil {


    private static ToastUtil instance = null;
    private Context mContext;

    private ToastUtil() {
    }

    public static ToastUtil getInstance() {
        synchronized (ToastUtil.class) {
            if (instance == null) {
                instance = new ToastUtil();
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public void showToast(String str) {
        if (null != mContext && !TextUtils.isEmpty(str)) {
            Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("ToastUtil", "请先初始化工具类");
        }
    }

}
