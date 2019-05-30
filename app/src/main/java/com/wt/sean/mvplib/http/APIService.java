package com.wt.sean.mvplib.http;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * *  类名：APIService   创建目的： "retrofit网络服务"
 *
 * @author 作者：wangtong
 * @date 时间:"2019/5/14 0014 14:07"
 */
public interface APIService {

    //服务器地址
    public static String HOST = "http://llv.dkyxj.com/api/";

    public static final long READTIMEOUT = 60;
    public static final long CONNECTTIME_OUT = 60;
    public static final long WRITETIMEOUT = 60;


}
