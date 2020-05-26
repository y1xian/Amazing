package com.yyxnb.http;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.yyxnb.common.log.LogUtils;
import com.yyxnb.http.interfaces.IData;
import com.yyxnb.http.rx.BaseHttpSubscriber;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

//    private OnHandleException onHandleException;
//
//    public void setOnHandleException(OnHandleException onHandleException) {
//        this.onHandleException = onHandleException;
//    }


    private final AppExecutors mAppExecutors = new AppExecutors();


    /**
     * 请求网络
     * @param flowable
     * @param <T>
     * @return
     */
    public <T> BaseHttpSubscriber<T> request(Flowable<T> flowable){
        BaseHttpSubscriber<T> baseHttpSubscriber = new BaseHttpSubscriber<T>(); //RxJava Subscriber回调
        flowable.subscribeOn(Schedulers.io()) //解决背压
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }



    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T> void launchOnlyresult(
            LiveData<ApiResponse<T>> call,
            OnHandleException<T> onHandleException
    ) {


//        Observer<ApiResponse<T>> apiResponseObserver = data -> {
//            LogUtils.e("---item----" + data.toString());
//            onHandleException.success(data.body);
//        };
//        call.observeForever(apiResponseObserver);
//        call.removeObserver(apiResponseObserver);


//        LiveData<T> map = Transformations.map(call, input -> {
//            LogUtils.e("---item-ss-in--" + input.getClass().getCanonicalName());
//
//
//            return null;
//        });


        MediatorLiveData<Resource<T>> mediatorLiveData = new MediatorLiveData<>();



        Runnable diskRunnable = () -> {

            Runnable mainRunnable = () -> {

                final LiveData<ApiResponse<T>> apiResponse = call;

                mediatorLiveData.addSource(apiResponse, data -> {
                    mediatorLiveData.removeSource(apiResponse);
                    LogUtils.e("---item-ss---" + data.toString());
//            onHandleException.success(data.body);
                });

            };

            mAppExecutors.mainThread().execute(mainRunnable);
        };

        mAppExecutors.diskIO().execute(diskRunnable);


        LogUtils.e("-------");

//        asLiveData.observeForever(data -> {
//            LogUtils.e("---item-ss---" + data.toString());
//            onHandleException.success(data.data);
//        });

//        return T;

//        return new NetworkResource<T>() {
//
////            @Override
////            protected T saveCallResult(@NonNull T item) {
////                LogUtils.e("---item--2222--" + item.toString());
////                onHandleException.success(item);
////                return super.saveCallResult(item);
////            }
////
////            @Override
////            protected void onFetchFailed() {
////                super.onFetchFailed();
////                onHandleException.error(null);
////            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<T>> createCall() {
//                return call;
//            }
//        }.getAsLiveData();

}

//    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
//
//    @NonNull
//    @Override
//    public Lifecycle getLifecycle() {
//        return mLifecycleRegistry;
//    }

public interface OnHandleException<T> {

    void success(T data);

    void error(Throwable t);

}

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
