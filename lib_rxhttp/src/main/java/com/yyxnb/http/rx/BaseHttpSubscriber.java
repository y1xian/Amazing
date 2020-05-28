package com.yyxnb.http.rx;

import android.arch.lifecycle.MutableLiveData;

import com.yyxnb.http.interfaces.IData;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * https://blog.csdn.net/longlonghaohao/java/article/details/103975307
 *
 * @param <T>
 */
public class BaseHttpSubscriber<T> implements Subscriber<T> {

    //异常类
    private ApiException ex;

    public BaseHttpSubscriber() {
        data = new MutableLiveData();
    }

    private MutableLiveData<T> data;

    public MutableLiveData<T> get() {
        return data;
    }

    public void set(T t) {
        this.data.setValue(t);
    }

    public void onFinish(T t) {
        set(t);
    }

    @Override
    public void onSubscribe(Subscription s) {
        // 观察者接收事件 = 1个
        s.request(1);
    }

    @Override
    public void onNext(T t) {
//        onFinish(t);
        if (t instanceof IData) {
            IData tt = (IData) t;
            if (tt.getCode().equals("200")) {
                onFinish((T) tt);
            } else {
                ex = ExceptionEngine.handleException(new ServerException(tt.getCode(), tt.getMsg()));
                getErrorDto(ex);
            }
        }

    }

    @Override
    public void onError(Throwable t) {
        ex = ExceptionEngine.handleException(t);
        getErrorDto(ex);
    }

    /**
     * 初始化错误的dto
     *
     * @param ex
     */
    private void getErrorDto(ApiException ex) {
        final IData dto = new IData() {
            @Override
            public String getCode() {
                return ex.getStatusCode();
            }

            @Override
            public String getMsg() {
                return ex.getStatusDesc();
            }

            @Override
            public Object getResult() {
                return null;
            }
        };
//        dto.setStatusCode(ex.getStatusCode());
//        dto.setStatusDesc(ex.getStatusDesc());
//        onFinish(dto.getResult());
    }

    @Override
    public void onComplete() {
    }

}