package com.yyxnb.http;

import com.yyxnb.http.interfaces.IRetrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 自定义配置
 */
public abstract class BaseRetrofitConfig implements IRetrofit {
    @Override
    public Retrofit initRetrofit(String baseUrl) {
        return BaseRetrofit.getInstance().create(baseUrl, this);
    }

    @Override
    public OkHttpClient initOkHttpClient() {
        return BaseOkHttpClient.getInstance().create();
    }
}
