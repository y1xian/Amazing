package com.yyxnb.amazing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yyxnb.amazing.fragments.TestFragment;
import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.utils.ActivityManagerUtils;
import com.yyxnb.common.log.LogUtils;

public class Test2Activity extends AppCompatActivity implements IActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test2);
//    }

    @BindViewModel
    TestViewModel viewModel;

    @Override
    public int initLayoutResId() {
        return R.layout.activity_test2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        viewModel.result.setValue("222222222222");

        LogUtils.list(ActivityManagerUtils.getInstance().getActivityList());

        viewModel.result.observe(this, s -> {
            LogUtils.e(s);
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mFrameLayout, new TestFragment(),"ggg")
                .commitAllowingStateLoss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getBaseDelegate().onWindowFocusChanged(hasFocus);
    }

    @Override
    public void initViewData() {
        LogUtils.e(":initViewData");
    }
}
