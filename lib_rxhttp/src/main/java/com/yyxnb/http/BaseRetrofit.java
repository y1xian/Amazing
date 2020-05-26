package com.yyxnb.http;

import com.yyxnb.http.utils.GsonUtils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofit {

    private volatile static BaseRetrofit baseRetrofit;

    public static BaseRetrofit getInstance() {
        if (baseRetrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (baseRetrofit == null) {
                    baseRetrofit = new BaseRetrofit();
                }
            }
        }
        return baseRetrofit;
    }

    public Retrofit create(String baseUrl, BaseRetrofitConfig retrofitConfig) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                .client(retrofitConfig.initOkHttpClient())
                .build();
    }
}
