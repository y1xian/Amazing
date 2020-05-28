package com.yyxnb.rx.subsciber;

import com.yyxnb.rx.exception.RxException;

/**
 * 简单的订阅者
 */
public abstract class SimpleSubscriber<T> extends BaseSubscriber<T> {
    /**
     * 出错
     *
     * @param e
     */
    @Override
    public void onError(RxException e) {
//        RxLog.e(e);
    }
}