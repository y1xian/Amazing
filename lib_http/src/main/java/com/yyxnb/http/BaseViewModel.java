package com.yyxnb.http;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.yyxnb.common.interfaces.IData;
import com.yyxnb.common.log.LogUtils;

public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

//    private OnHandleException onHandleException;
//
//    public void setOnHandleException(OnHandleException onHandleException) {
//        this.onHandleException = onHandleException;
//    }


    private final AppExecutors mAppExecutors = new AppExecutors();

//    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <T extends IData> void launchOnlyresult(
            LiveData<T> call,
            OnHandleException<T> onHandleException
    ) {


        Observer<T> apiResponseObserver = data -> {
//            LogUtils.e("---item----" + data.toString() + " , " + data.getResult()
//                    + " , "+ (data instanceof IData)
//                    + " , "+ (data.getResult() instanceof IData));

//            if (data instanceof IData){

                LogUtils.d("data : " + data.toString());
//                if (data.getResult() != null){
                    onHandleException.success(data);
//                }

//                onHandleException.success((R)data.getResult());
//                onHandleException.success((IData<R>) data.getResult());
//            }else {
//                onHandleException.error("非IData");
//            }


//            executeResponse(data);
        };
        call.observeForever(apiResponseObserver);
//        call.removeObserver(apiResponseObserver);


//        LiveData<T> map = Transformations.map(call, input -> {
//            LogUtils.e("---item-ss-in--" + input.getClass().getCanonicalName());
//
//
//            return null;
//        });


//        MediatorLiveData<Resource<T>> mediatorLiveData = new MediatorLiveData<>();
//
//        Runnable diskRunnable = () -> {
//
//            Runnable mainRunnable = () -> {
//
//                final LiveData<T> apiResponse = call;
//
//                mediatorLiveData.addSource(apiResponse, data -> {
//                    mediatorLiveData.removeSource(apiResponse);
//                    LogUtils.e("---item-ss---" + data.toString());
////            onHandleException.success(data.body);
//                });
//
//            };
//
//            mAppExecutors.mainThread().execute(mainRunnable);
//        };
//
//        mAppExecutors.diskIO().execute(diskRunnable);


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


public  interface OnHandleException<T> {

    void success(T data);

    void error(String msg);

}

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
