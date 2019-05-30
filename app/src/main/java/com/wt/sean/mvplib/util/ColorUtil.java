package com.wt.sean.mvplib.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ColorUtil {

    private Context mContext;
    private static ColorUtil instance = null;

    private ColorUtil() {
    }

    public static ColorUtil getInstance() {
        synchronized (ColorUtil.class) {
            if (instance == null) {
                instance = new ColorUtil();
            }
        }
        return instance;
    }

    public void init(Context context){
        this.mContext = context;
    }


    public int getColor(int resId){
        return ContextCompat.getColor(mContext,resId);
    }

}
