package com.yyxnb.http.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Map<String, String> map = new HashMap<>();

    public HeaderInterceptor(Map<String, String> header) {
        this.map = header;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
        map.put("Accept-Encoding", "gzip");
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json; charset=utf-8");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        requestBuilder.method(originalRequest.method(), originalRequest.body());
        return chain.proceed(requestBuilder.build());
    }

}