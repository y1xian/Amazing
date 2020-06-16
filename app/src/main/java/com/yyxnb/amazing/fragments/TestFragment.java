package com.yyxnb.amazing.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.amazing.R;
import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.common.AppConfig;

/**
 * 实现IFragment.
 */
@BindRes(subPage = true)
public class TestFragment extends Fragment implements IFragment {

    @BindViewModel
    TestViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppConfig.getInstance().log("TestFragment initView");
    }

    @Override
    public void initObservable() {

//        viewModel.result.postValue("333333");
//
//        viewModel.result.observe(this, s -> {
//            LogUtils.e("test : " + s);
//        });
    }

    @Override
    public void initViewData() {
        AppConfig.getInstance().log("initViewData : " + hashCode());
    }

    @Override
    public void onVisible() {
        AppConfig.getInstance().log("onVisible test: " + hashCode());
    }

    @Override
    public void onInVisible() {
        AppConfig.getInstance().log("onInVisible test: " + hashCode());
    }

    // vp下 要自实现setUserVisibleHint
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getBaseDelegate().setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getBaseDelegate().onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseDelegate().onConfigurationChanged(newConfig);
    }
}
