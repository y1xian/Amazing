package com.yyxnb.arch.base;

import android.os.Bundle;

import com.yyxnb.arch.delegate.FragmentDelegate;

public interface IFragment extends IView {

    default FragmentDelegate getBaseDelegate() {
        return new FragmentDelegate(this);
    }

    /**
     * 用户可见时候调用
     */
    default void onVisible() {
    }

    /**
     * 用户不可见时候调用
     */
    default void onInVisible() {
    }

    default Bundle initArguments() {
        return null;
    }

    default String sceneId() {
        return null;
    }

}
