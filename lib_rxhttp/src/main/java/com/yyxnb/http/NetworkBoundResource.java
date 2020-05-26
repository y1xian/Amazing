package com.yyxnb.http;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * <p>
 * 有关NetworkBoundResource的设计意图可以参考： https://developer.android.com/topic/libraries/architecture/guide.html#addendum
 * <p>
 * Test Case :
 * 1. db success without network
 * 2. db success with network success
 * 3. db success with network failure
 * 4. network success
 * 5. network failure
 *
 * @param <R>
 */
public abstract class NetworkBoundResource<R> {

    private final AppExecutors mAppExecutors = new AppExecutors();

    private MediatorLiveData<Resource<R>> mResult = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
//        mAppExecutors = appExecutors;

        mResult.setValue(Resource.<R>loading(null));

        final LiveData<R> dbSource = loadFromDb();

        mResult.addSource(dbSource, data -> {
            mResult.removeSource(dbSource);

            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                mResult.addSource(dbSource, newData -> mResult.setValue(Resource.success(newData)));
            }
        });

    }

    private void fetchFromNetwork(final LiveData<R> dbSource) {

        final LiveData<ApiResponse<R>> apiResponse = createCall();

        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        mResult.addSource(dbSource, newData -> mResult.setValue(Resource.loading(newData)));

        mResult.addSource(apiResponse, response -> {

            mResult.removeSource(apiResponse);
            mResult.removeSource(dbSource);

            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                mAppExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    mAppExecutors.mainThread().execute(() -> {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        mResult.addSource(loadFromDb(), newData -> mResult.setValue(Resource.success(newData)));
                    });
                });
            } else {
                onFetchFailed();

                mResult.addSource(dbSource, newData -> mResult.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public final LiveData<Resource<R>> getAsLiveData() {
        return mResult;
    }

    // Called to insertItem the mResult of the API response into the database
    @WorkerThread
    protected void saveCallResult(@NonNull R item){};

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected boolean shouldFetch(@Nullable R data) {
        return true;
    }

    // Called to get the cached data from the database
    @NonNull
    @MainThread
    protected LiveData<R> loadFromDb() {
        MutableLiveData<R> data = new MutableLiveData<>();
        data.setValue(null);
        return data;
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<R>> createCall();

    @WorkerThread
    protected R processResponse(ApiResponse<R> response) {
        return response.body;
    }

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }
}