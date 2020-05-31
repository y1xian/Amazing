package com.yyxnb.amazing.api;

import com.yyxnb.amazing.bean.BaseData;
import com.yyxnb.amazing.bean.StateData;
import com.yyxnb.amazing.bean.TikTokBean;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IService {

//    @GET("video/query")
    @GET("v2/5ecf5d7f3200002c00e3d0e4")
    Flowable<BaseData<StateData<TikTokBean>>> getVideoList3(@QueryMap Map<String,String> map);

//    @GET("v2/5ecfd21e320000f1aee3d61a")
    @GET("video/query")
    Observable<BaseData<StateData<TikTokBean>>> getVideoList4(@QueryMap Map<String,String> map);
}
