package com.yyxnb.amazing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;

public class TestActivity extends AppCompatActivity implements IActivity {

    @BindViewModel
    TestViewModel viewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppConfig.getInstance().log("1 initView");
    }

    @Override
    public void initViewData() {
//        startFragment(TitleFragment.newInstance());
        AppConfig.getInstance().log("1 initViewData");

        viewModel.result.setValue("11111111111111");

        viewModel.result.observe(this,s -> {
            LogUtils.e(s);
        });
    }

//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_test;
//    }

//    @Override
//    public void initView() {
//        AppConfig.getInstance().log("2 initView");
//    }
//
//    @Override
//    public void initData() {
//        AppConfig.getInstance().log("2 initData");
//        LogUtils.list(ActivityManagerUtils.getInstance().getActivityList());
//    }
}
