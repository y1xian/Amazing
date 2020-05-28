package com.yyxnb.http.interfaces;

/**
 * 接口返回封装类
 * @param <T>
 */
public interface IData<T> {

    String getCode();

    String getMsg();

    T getResult();

    boolean isSuccess();
}
