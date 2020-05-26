package com.yyxnb.arch.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.arch.delegate.FragmentDelegate;

import java.lang.ref.WeakReference;
import java.util.UUID;

public abstract class BaseFragment extends Fragment implements IFragment {

    protected final String TAG = getClass().getCanonicalName();

    protected WeakReference<AppCompatActivity> mActivity;
    protected WeakReference<Context> mContext;
    protected View mRootView;

    private FragmentDelegate mFragmentDelegate;
    private Java8Observer java8Observer;

    @Nullable
    @Override
    public Context getContext() {
        return mContext.get();
    }

    @Override
    public FragmentDelegate getBaseDelegate() {
        return mFragmentDelegate;
    }

    public BaseFragment() {
        java8Observer = new Java8Observer(TAG);
        getLifecycle().addObserver(java8Observer);
        mFragmentDelegate = new FragmentDelegate(this);
    }

    public <B extends ViewDataBinding> B getBinding() {
//        DataBindingUtil.bind<B>(mRootView);
        DataBindingUtil.bind(mRootView);
        return DataBindingUtil.getBinding(mRootView);
    }

    public String sceneId() {
        return UUID.randomUUID().toString();
    }

    public int hasId() {
        assert TAG != null;
        return Math.abs(TAG.hashCode());
    }

    public Bundle initArguments() {
        return mFragmentDelegate.initArguments();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        requireActivity().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                mContext = new WeakReference<>(context);
                mActivity = new WeakReference<>((AppCompatActivity) mContext.get());
                mFragmentDelegate.onAttach(mActivity.get());
                owner.getLifecycle().removeObserver(this);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentDelegate.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = mFragmentDelegate.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentDelegate.onActivityCreated(savedInstanceState);
        //当设备旋转时，fragment会随托管activity一起销毁并重建。
//        retainInstance = true
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mFragmentDelegate.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mFragmentDelegate.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFragmentDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentDelegate.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentDelegate.onDestroy();
        mFragmentDelegate = null;
        result = null;
        mContext.clear();
        mContext = null;
        mRootView = null;
        mActivity.clear();
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentDelegate.onDestroyView();
        getLifecycle().removeObserver(java8Observer);
    }

    @Override
    public void initViewData() {
        mFragmentDelegate.initDeclaredFields();
    }

    @SuppressWarnings("unchecked")
    public <T> T findViewById(@IdRes int resId) {
        return (T) mRootView.findViewById(resId);
    }

    /**
     * 返回.
     */
    public void finish() {
        mActivity.get().onBackPressed();
    }

    public <T extends BaseFragment> void startFragment(T targetFragment) {
        startFragment(targetFragment, 0);
    }

    public <T extends BaseFragment> void startFragment(T targetFragment, int requestCode) {
        mFragmentDelegate.startFragment(targetFragment, requestCode);
    }

    public int requestCode;
    public int resultCode;
    public Intent result;

    public void setResult(int resultCode, Intent result) {
        this.resultCode = resultCode;
        this.result = result;
    }

}
