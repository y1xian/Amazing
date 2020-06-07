package com.yyxnb.http;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.yyxnb.common.interfaces.IData;

public abstract class NetworkData<R,T> {

    private final AppExecutors mAppExecutors = new AppExecutors();

    private MediatorLiveData<T> mResult = new MediatorLiveData<>();

    @MainThread
    public NetworkData() {


        Runnable diskRunnable = () -> {
            onPreProcess();
            Runnable mainRunnable = () -> {
                final LiveData<IData<R>> apiResponse = (LiveData<IData<R>>) createCall();
                mResult.addSource(apiResponse, response -> {
                    mResult.removeSource(apiResponse);

                    //noinspection ConstantConditions
                    if (response != null) {
                        mAppExecutors.diskIO().execute(() -> {
                            final T newData = saveCallResult(processResponse((T) response.getResult()));
                            mAppExecutors.mainThread().execute(() -> mResult.setValue(newData));
                        });
                    } else {
                        onFetchFailed();
                        mResult.setValue(null);
                    }
                });
            };
            mAppExecutors.mainThread().execute(mainRunnable);
        };
        mAppExecutors.diskIO().execute(diskRunnable);
    }

    @WorkerThread
    protected void onPreProcess() {
    }

    @WorkerThread
    protected T processResponse(T response) {
        return response;
    }

    // Called to insertItem the mResult of the API response into the database
    @WorkerThread
    protected T saveCallResult(@NonNull T item) {
        return item;
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<R> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public final LiveData<T> getAsLiveData() {
        return mResult;
    }
}