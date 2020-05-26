package com.yyxnb.http.interfaces;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public interface IRetrofit {

    Retrofit initRetrofit(String baseUrl);

    OkHttpClient initOkHttpClient();

}
