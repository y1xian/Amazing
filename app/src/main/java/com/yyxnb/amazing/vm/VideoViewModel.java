package com.yyxnb.amazing.vm;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.yyxnb.amazing.bean.TikTokBean;
import com.yyxnb.http.BasePagedViewModel;

import java.util.Collections;

public class VideoViewModel extends BasePagedViewModel<TikTokBean> {
    @Override
    public DataSource createDataSource() {
        return new PageKeyedDataSource<Integer,TikTokBean>(){

            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TikTokBean> callback) {
                callback.onResult(Collections.emptyList(),null,null);
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TikTokBean> callback) {
                callback.onResult(Collections.emptyList(),null);
            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TikTokBean> callback) {
                callback.onResult(Collections.emptyList(),null);
            }
        };
    }
}
