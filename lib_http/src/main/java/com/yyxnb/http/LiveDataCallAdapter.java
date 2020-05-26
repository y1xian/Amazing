package com.yyxnb.http;

import android.arch.lifecycle.LiveData;

import com.yyxnb.http.interfaces.IData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<R>> {

    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<R> adapt(final Call<R> call) {

        return new LiveData<R>() {

            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(response.body());
//                            postValue(new IData<R>() {
//                                @Override
//                                public String getCode() {
//                                    return "200";
//                                }
//
//                                @Override
//                                public String getMsg() {
//                                    return response.message();
//                                }
//
//                                @Override
//                                public R getResult() {
//                                    return response.body();
//                                }
//                            });
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
//                            postValue(new ApiResponse<R>(throwable));
                            postValue(null);
                        }
                    });
                }
            }
        };
    }
}