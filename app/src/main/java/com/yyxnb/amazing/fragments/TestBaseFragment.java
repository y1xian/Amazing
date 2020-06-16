package com.yyxnb.amazing.fragments;

import android.os.Bundle;

import com.yyxnb.amazing.R;
import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;

/**
 * 继承BaseFragment.
 */
@BindRes(layoutRes = R.layout.fragment_test_base, subPage = true)
public class TestBaseFragment extends BaseFragment {

    @BindViewModel
    TestViewModel viewModel;

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initObservable() {
        viewModel.result.observe(this, s -> {
            LogUtils.e("testBase : " + s);
        });
    }

    @Override
    public void initViewData() {
        AppConfig.getInstance().log("initViewData testbase: " + hashCode());
    }

    @Override
    public void onVisible() {
        AppConfig.getInstance().log("onVisible testbase: " );
    }

    @Override
    public void onInVisible() {
        AppConfig.getInstance().log("onInVisible testbase: " );
    }
}