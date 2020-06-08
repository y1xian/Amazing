package com.yyxnb.amazing.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.view.titlebar.TitleBar;


/**
 * 标题栏.
 */
@BindRes(layoutRes = R.layout.fragment_title)
public class TitleFragment extends BaseFragment {

//    @BindViewModel(isActivity = true)
//    NetWorkViewModel mViewModel;
//    @BindViewModel
//    MsgViewModel msgViewModel;

    private TitleBar mTitleBar;

    public static TitleFragment newInstance() {

        Bundle args = new Bundle();

        TitleFragment fragment = new TitleFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

//        LogUtils.INSTANCE.e("" + getHasId() + ", " + getSceneId());

        mTitleBar.setBackListener(v -> {
            finish();
        });

    }

    @Override
    public void initViewData() {
        super.initViewData();
//        if (mViewModel == null){
//            LogUtils.INSTANCE.w(" null ");
//        }else {
//            LogUtils.INSTANCE.w("---- " + mViewModel);
//            mViewModel.reqTeam();
//        }
//        if (msgViewModel == null){
//            LogUtils.INSTANCE.w(" null ");
//        }else {
//            LogUtils.INSTANCE.w("---- " + msgViewModel);
//            msgViewModel.reqMsg("6666666666");
//        }

    }

    @Override
    public void initObservable() {
//        LogUtils.INSTANCE.e("initObservable");
    }

//    @Override
//    public void handleEvent(@Nullable MsgEvent msg) {
//        LogUtils.INSTANCE.e("msg : " + msg.getMsg());
//    }

    @Override
    public void onVisible() {
        super.onVisible();
//        LogUtils.INSTANCE.e("onVisible");
    }

    @Override
    public void onInVisible() {
        super.onInVisible();
//        LogUtils.INSTANCE.e("onInVisible");
    }

}
