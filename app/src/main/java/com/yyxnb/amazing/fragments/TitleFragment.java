package com.yyxnb.amazing.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.amazing.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;
import com.yyxnb.view.titlebar.TitleBar;


/**
 * 标题栏.
 */
@BindRes(layoutRes = R.layout.fragment_title, subPage = true)
public class TitleFragment extends BaseFragment {

    private TitleBar mTitleBar;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mTitleBar = findViewById(R.id.mTitleBar);

//        mTitleBar.setBackgroundResource(R.drawable.shape_gradient_bg);
        mTitleBar.showCenterProgress();

//        mTitleBar.setClickListener((v, action) -> {
//            switch (action) {
//                case ACTION_LEFT_BUTTON:
//                    finish();
//                    break;
//                default:
//                    break;
//            }
//        });

        mTitleBar.setBackListener(v -> {
            finish();
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();
        LogUtils.e("initViewData title");
    }

    @Override
    public void initObservable() {
    }

    @Override
    public void onVisible() {
        LogUtils.e("onVisible title");
    }

    @Override
    public void onInVisible() {
        LogUtils.e("onInVisible title");
    }

}
