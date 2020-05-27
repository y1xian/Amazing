package com.yyxnb.arch.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.lang.reflect.Field;

public class FragmentDelegateImpl implements IFragmentDelegate {

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

    }

    @Override
    public void onCreated(Bundle savedInstanceState) {
        Class<?> clazz = fragment.getClass().getSuperclass();
        if (clazz != null) {
            while (!(clazz.getName()).equals(Fragment.class.getName())) {
                clazz = clazz.getSuperclass();
            }
            try {
                Field field = clazz.getDeclaredField("mContentLayoutId");
                field.setAccessible(true);
                field.set(fragment, iFragment.getLayoutId());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        iFragment.initView();
        iFragment.initData();
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onResumed() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStoped() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void onDestroyed() {
        this.fragmentManager = null;
        this.fragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetached() {

    }

    @Override
    public Boolean isAdd() {
        return fragment.isAdded();
    }
}
