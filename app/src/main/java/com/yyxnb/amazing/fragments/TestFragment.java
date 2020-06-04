package com.yyxnb.amazing.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.amazing.R;
import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements IFragment {

    public TestFragment() {
        // Required empty public constructor
    }

    @BindViewModel(isActivity = true)
    TestViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

//    @Override
//    public int initLayoutResId() {
//        return R.layout.fragment_test;
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppConfig.getInstance().log("TestFragment initView: " + 222);

//        viewModel.result.setValue("333333");

        viewModel.result.observe(this, s -> {
//            LogUtils.e("test : " + s);
        });
    }

    @Override
    public void initViewData() {
        LogUtils.w("initViewData : " + hashCode());
    }

    @Override
    public void onVisible() {
        LogUtils.w("onVisible : " + hashCode());
    }

    @Override
    public void onInVisible() {
        LogUtils.w("onInVisible : " + hashCode());
    }
}
