package com.yyxnb.http;

import com.yyxnb.http.annotations.BaseUrl;
import com.yyxnb.http.annotations.BindUrl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

/**
 * retrofit 工厂类
 */
public class RetrofitFactory {

    private volatile static RetrofitFactory retrofitFactory;

    public static RetrofitFactory getInstance() {
        if (retrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofitFactory == null) {
                    retrofitFactory = new RetrofitFactory();
                }
            }
        }
        return retrofitFactory;
    }

    public Map<String, HttpUrl> urlMap = new HashMap<>();


    public <T> T create(Class<T> clz) {
        return create(clz, null);
    }

    public <T> T create(Class<T> clz, BaseRetrofitConfig retrofitConfig) {
        String baseUrl = prepareBaseUrl(clz);
        prepareOtherUrls(clz);

        // 判断是否, 单独设置了 retrofitConfig, 否则默认按照全局 RetrofitConfig 配置
        Retrofit retrofit = null;
        if (retrofitConfig != null) {
            retrofit = retrofitConfig.initRetrofit(baseUrl);
        } else {
            retrofit = HttpConfig.getInstance().getRetrofitConfig().initRetrofit(baseUrl);
        }
        return retrofit.create(clz);
    }

    private <T> void prepareOtherUrls(Class<T> clz) {
        Annotation annotation = clz.getAnnotation(BindUrl.class);

        if (annotation != null) {
            for (String url : urlMap.keySet()) {
                urlMap.put(url, Objects.requireNonNull(HttpUrl.parse(url)));
            }
        }
    }

    private <T> String prepareBaseUrl(Class<T> clz) {
        BaseUrl baseUrlAnnotation = clz.getAnnotation(BaseUrl.class);
        if (baseUrlAnnotation != null) {
            return baseUrlAnnotation.value();
        } else {
            throw new IllegalArgumentException("base url is null");
        }
    }

}
