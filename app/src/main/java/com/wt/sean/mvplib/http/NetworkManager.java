package com.wt.sean.mvplib.http;


import com.wt.sean.mvplib.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager instance = null;

    private static Retrofit retrofit;
    private static volatile APIService apiService = null;

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        synchronized (NetworkManager.class) {
            if (instance == null) {
                instance = new NetworkManager();
            }
        }
        return instance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        // 初始化okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

        //LOG
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        builder.addInterceptor(loggingInterceptor);

        //设置头
        builder.addInterceptor(headerInterceptor);
        builder.connectTimeout(APIService.CONNECTTIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(APIService.READTIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(APIService.WRITETIMEOUT, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIService.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIService getApiService() {
        if (apiService == null) {
            synchronized (Request.class) {
                apiService = retrofit.create(APIService.class);
            }
        }
        return apiService;
    }

}
