package com.yyxnb.arch.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

import com.yyxnb.arch.delegate.FragmentDelegate;
import com.yyxnb.arch.utils.AppManager;

public interface IFragment extends IView {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default FragmentDelegate getBaseDelegate() {
        FragmentDelegate delegate = AppManager.getInstance().getFragmentDelegates().get(hashCode());
        if (delegate == null) {
            delegate = new FragmentDelegate(this);
            AppManager.getInstance().getFragmentDelegates().put(hashCode(), delegate);
        }
        return delegate;
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
