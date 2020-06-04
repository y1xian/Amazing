package com.yyxnb.amazing.fragments.vp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.common.log.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Vp1Fragment extends Fragment implements IFragment {

    public static Vp1Fragment newInstance() {

        Bundle args = new Bundle();

        Vp1Fragment fragment = new Vp1Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vp1, container, false);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initViewData() {
        LogUtils.w("initViewData 1: " + hashCode());
    }

    @Override
    public void onVisible() {
        LogUtils.w("onVisible 1: " + hashCode());
    }

    @Override
    public void onInVisible() {
        LogUtils.w("onInVisible 1: " + hashCode());
    }

}