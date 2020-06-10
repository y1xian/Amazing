package com.yyxnb.http.rx;

import com.yyxnb.common.AppConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {

    private int maxRetries = 2;//最大出错重试次数
    private int retryDelayMillis = 3000;//重试间隔时间
    private int retryCount = 0;//当前出错重试次数

    public RetryWithDelay() {
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                    if (++retryCount <= maxRetries) {
                        AppConfig.getInstance().log("get error, it will try after " + retryDelayMillis * retryCount
                                + " millisecond, retry count " + retryCount);
                        // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                        return Observable.timer(retryDelayMillis * retryCount,
                                TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(throwable);
                });
    }
}