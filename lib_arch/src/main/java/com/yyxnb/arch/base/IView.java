package com.yyxnb.arch.base;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

public interface IView extends LifecycleObserver {

    /**
     * 初始化布局
     */
    @LayoutRes
    default int initLayoutResId() {
        return 0;
    }

    /**
     * 初始化控制、监听等轻量级操作
     */
    void initView(Bundle savedInstanceState);

    /**
     * 处理重量级数据、逻辑
     */
    default void initViewData() {
    }

    /**
     * 初始化界面观察者的监听
     * 接收数据结果
     */
    default void initObservable() {
    }
}
