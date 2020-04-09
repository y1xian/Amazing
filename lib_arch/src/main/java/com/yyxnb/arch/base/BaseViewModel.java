package com.yyxnb.arch.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
