package com.yyxnb.amazing;

import android.os.Bundle;

import com.yyxnb.amazing.fragments.TitleFragment;
import com.yyxnb.arch.base.BaseActivity;
import com.yyxnb.arch.delegate.IActivity;

public class TestActivity extends BaseActivity implements IActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//    }

    @Override
    public int initLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {
        startFragment(TitleFragment.newInstance());
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean initArgs(Bundle extras) {
        return true;
    }
}
