package com.yyxnb.amazing;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yyxnb.amazing.fragments.TestFragment;
import com.yyxnb.amazing.vm.TestViewModel;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.utils.ActivityManagerUtils;
import com.yyxnb.common.log.LogUtils;

public class Test2Activity extends AppCompatActivity implements IActivity , LoaderManager.LoaderCallbacks<Void>{

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
//            LogUtils.e(s);
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mFrameLayout, new TestFragment(),"ggg")
                .commitAllowingStateLoss();


    }

    @Override
    public void initViewData() {
        LogUtils.e(":initViewData");
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        reportFullyDrawn();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
