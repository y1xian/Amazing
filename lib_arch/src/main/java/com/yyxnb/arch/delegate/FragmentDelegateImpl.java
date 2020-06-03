package com.yyxnb.arch.delegate;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.arch.livedata.ViewModelFactory;
import com.yyxnb.common.MainThreadUtils;
import com.yyxnb.common.log.LogUtils;

import java.lang.reflect.Field;

public class FragmentDelegateImpl implements IFragmentDelegate, LifecycleObserver {

    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;

    private IFragment iFragment = null;

    public FragmentDelegateImpl(Fragment fragment, FragmentManager fragmentManager) {
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttached(Context context) {
        iFragment.getBaseDelegate().onAttach(context);
        LogUtils.e("  onAttached ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreated(Bundle savedInstanceState) {
        iFragment.getBaseDelegate().onCreate(savedInstanceState);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initDeclaredFields();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        iFragment.getBaseDelegate().onActivityCreated(savedInstanceState);
//        iFragment.initView(savedInstanceState);
//        iFragment.initViewData();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void onStarted() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResumed() {
        iFragment.getBaseDelegate().onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPaused() {
        iFragment.getBaseDelegate().onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStoped() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onViewDestroyed() {
        iFragment.getBaseDelegate().onDestroyView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroyed() {
        iFragment.getBaseDelegate().onDestroy();
        this.fragmentManager = null;
        this.fragment = null;
        this.iFragment = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDetached() {
    }

    @Override
    public boolean isAdd() {
        return fragment.isAdded();
    }

    public void initDeclaredFields() {
        MainThreadUtils.post(() -> {
            Field[] declaredFields = iFragment.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                // 允许修改反射属性
                field.setAccessible(true);

                /**
                 *  根据 @BindViewModel 注解, 查找注解标示的变量（ViewModel）
                 *  并且 创建 ViewModel 实例, 注入到变量中
                 */
                BindViewModel annotation = field.getAnnotation(BindViewModel.class);
                if (annotation != null) {
                    try {
                        field.set(iFragment, getViewModel(field, annotation.isActivity()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public ViewModel getViewModel(Field field, boolean isActivity) {
        if (isActivity) {
            return ViewModelFactory.createViewModel(fragment.getActivity(), field);
        } else {
            return ViewModelFactory.createViewModel(fragment, field);
        }
    }
}
