package com.yyxnb.amazing.data;

import com.yyxnb.http.AbstractHttp;

public class Http extends AbstractHttp {

    private volatile static Http http;

    public static Http getInstance() {
        if (http == null) {
            synchronized (Http.class) {
                if (http == null) {
                    http = new Http();
                }
            }
        }
        return http;
    }

    @Override
    protected String baseUrl() {
        return DataConfig.BASE_URL;
    }


}
