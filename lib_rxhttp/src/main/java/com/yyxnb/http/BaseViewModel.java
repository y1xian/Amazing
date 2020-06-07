package com.yyxnb.http;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import com.yyxnb.common.AppConfig;
import com.yyxnb.common.interfaces.IData;
import com.yyxnb.http.rx.BaseHttpSubscriber;
import com.yyxnb.rx.DisposablePool;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    private final AppExecutors mAppExecutors = new AppExecutors();

    /**
     * 请求网络
     *
     * @param flowable
     * @param <T>
     * @return
     */
    public <T extends IData> BaseHttpSubscriber<T> request(Flowable<T> flowable) {
        final BaseHttpSubscriber<T> baseHttpSubscriber = new BaseHttpSubscriber<T>();
        //RxJava Subscriber回调
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseHttpSubscriber);
        return baseHttpSubscriber;
    }

    public <T extends IData> LiveData<T> request2(Observable<T> observable) {
        return new ObservableLiveData<>(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()));
    }

    public <T extends IData> void launchOnlyResult(
            Observable<T> call,
            OnHandleException<T> onHandleException
    ) {

//        Runnable diskRunnable = () -> {
//
//            Runnable mainRunnable = () -> {

        MediatorLiveData<T> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(request2(call), t -> {

            AppConfig.getInstance().log(" ttt " + t);
//                    mediatorLiveData.removeSource(request2(call));
//            mediatorLiveData.setValue((T) t.getResult());
            onHandleException.success(t);
        });

//            }

//            mAppExecutors.mainThread().execute(mainRunnable);
//        };
//
//        mAppExecutors.diskIO().execute(diskRunnable);


    }

    public interface OnHandleException<T> {

        void success(T data);

        void error(String msg);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        DisposablePool.get().removeAll();
    }
}
