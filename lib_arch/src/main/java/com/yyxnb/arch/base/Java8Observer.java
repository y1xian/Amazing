package com.yyxnb.arch.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.yyxnb.common.AppConfig;

import java.io.Serializable;

public class Java8Observer implements DefaultLifecycleObserver, Serializable {

    private String tag;

    public Java8Observer(String tag) {
        this.tag = tag;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        AppConfig.getInstance().log(tag, owner.getLifecycle().getCurrentState().name());
    }
}
