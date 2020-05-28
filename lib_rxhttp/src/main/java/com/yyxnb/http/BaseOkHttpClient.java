package com.yyxnb.http;

import com.yyxnb.http.interceptor.HeaderInterceptor;
import com.yyxnb.http.interceptor.LoggingInterceptor;
import com.yyxnb.http.interceptor.UrlInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseOkHttpClient {

    private volatile static BaseOkHttpClient baseOkHttpClient;

    public static BaseOkHttpClient getInstance() {
        if (baseOkHttpClient == null) {
            synchronized (BaseOkHttpClient.class) {
                if (baseOkHttpClient == null) {
                    baseOkHttpClient = new BaseOkHttpClient();
                }
            }
        }
        return baseOkHttpClient;
    }

    // 初始化 okHttp
    public OkHttpClient create(Interceptor... interceptors) {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        for (Interceptor it : interceptors) {
            builder.addInterceptor(it);
        }

        final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(new HeaderInterceptor(new HashMap<>()))
                .addInterceptor(new UrlInterceptor())
                .readTimeout(HttpConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(HttpConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor);

        return builder.build();
    }
}
