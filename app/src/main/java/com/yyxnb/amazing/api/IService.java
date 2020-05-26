package com.yyxnb.amazing.api;

import android.arch.lifecycle.LiveData;

import com.yyxnb.amazing.bean.BaseData;
import com.yyxnb.amazing.bean.StateData;
import com.yyxnb.amazing.bean.TikTokBean;
import com.yyxnb.http.ApiResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IService {

//    @FormUrlEncoded
//    @POST("video/query")
//    LiveData<ApiResponse<BaseListData<TikTokBean>>> getVideoList(@FieldMap Map<String,String> map);

    @GET("video/query")
    LiveData<ApiResponse<BaseData<StateData<TikTokBean>>>> getVideoList(@QueryMap Map<String,String> map);

    @GET("video/query")
    LiveData<BaseData<StateData<TikTokBean>>> getVideoList2(@QueryMap Map<String,String> map);

//    @GET("video/query")
//    Flowable<BaseData<StateData<TikTokBean>>> getVideoList3(@QueryMap Map<String,String> map);
}
