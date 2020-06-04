package com.yyxnb.amazing.fragments.vp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.annotations.BindFragment;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@BindFragment(subPage = true)
public class Vp3Fragment extends BaseFragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_vp3, container, false);
//    }

    public static Vp3Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        Vp3Fragment fragment = new Vp3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_vp3;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LogUtils.w(" -- initView - 3");
    }

    @Override
    public void initViewData() {
        LogUtils.w(" -- initViewData - 3");
    }

    @Override
    public void onVisible() {
        LogUtils.w("onVisible 3: " + hashCode());
    }

    @Override
    public void onInVisible() {
        LogUtils.w("onInVisible 3: " + hashCode());
    }
}