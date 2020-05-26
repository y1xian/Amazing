package com.yyxnb.http;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

public abstract class NetworkResource<R> {

    private final AppExecutors mAppExecutors = new AppExecutors();

    private MediatorLiveData<Resource<R>> mResult = new MediatorLiveData<>();

    @MainThread
    public NetworkResource() {
//        mAppExecutors = appExecutors;

        mResult.setValue(Resource.<R>loading(null));

        Runnable diskRunnable = () -> {
            onPreProcess();
            Runnable mainRunnable = () -> {
                final LiveData<ApiResponse<R>> apiResponse = createCall();
                mResult.addSource(apiResponse, response -> {
                    mResult.removeSource(apiResponse);

                    //noinspection ConstantConditions
                    if (response.isSuccessful()) {
                        mAppExecutors.diskIO().execute(() -> {
                            final R newData = saveCallResult(processResponse(response));
                            mAppExecutors.mainThread().execute(() -> mResult.setValue(Resource.success(newData)));
                        });
                    } else {
                        onFetchFailed();
                        mResult.setValue(Resource.<R>error(response.errorMessage, null));
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
    protected R processResponse(ApiResponse<R> response) {
        return response.body;
    }

    // Called to insertItem the mResult of the API response into the database
    @WorkerThread
    protected R saveCallResult(@NonNull R item) {
        return item;
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<R>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public final LiveData<Resource<R>> getAsLiveData() {
        return mResult;
    }
}