package com.yyxnb.amazing;

import android.os.Bundle;

import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.amazing.base.BaseActivity;
import com.yyxnb.common.log.LogUtils;

@BindRes(layoutRes = R.layout.activity_test_base, fitsSystemWindows = true, statusBarColor = R.color.black_40, statusBarStyle = BarStyle.LightContent)
public class TestBaseActivity extends BaseActivity {

    @BindViewModel
    TestViewModel viewModel;

    @Override
    public void initView(Bundle savedInstanceState) {
        viewModel.result.setValue("222222222222");

        viewModel.result.observe(this, s -> {
//            LogUtils.e(s);
        });

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.mFrameLayout, new TestFragment(), "ggg")
//                .commitAllowingStateLoss();


    }

    @Override
    public void initViewData() {
        LogUtils.e(":initViewData");
    }
}
