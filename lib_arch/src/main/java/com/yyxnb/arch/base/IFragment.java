package com.yyxnb.arch.base;

import android.os.Bundle;

import com.yyxnb.arch.delegate.FragmentDelegate;

public interface IFragment extends IView{

    FragmentDelegate getBaseDelegate();

    /**
     * 用户可见时候调用
     */
    default void onVisible(){}

    /**
     * 用户不可见时候调用
     */
    default void onInVisible(){}

}
