package com.yyxnb.arch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yyxnb.common.AppConfig;

/**
 * 监听应用状态
 */
public class AppLifeObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onForeground() {
        AppConfig.getInstance().log("应用进入前台");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onBackground() {
        AppConfig.getInstance().log("应用进入后台");
    }
}
