package com.yyxnb.amazing.popup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.yyxnb.amazing.R;
import com.yyxnb.amazing.fragments.VpMainFragment;
import com.yyxnb.view.popup.PopupUtils;
import com.yyxnb.view.popup.code.BottomPopup;

/**
 * vp
 */
public class VpBottomPopup extends BottomPopup {

    public VpBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int initLayoutResId() {
        return R.layout.popup_vp_bottom_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.ivDel).setOnClickListener(v -> dismiss());

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mFrameLayout, new VpMainFragment());
        ft.commitAllowingStateLoss();
    }

    @Override
    protected int getPopupHeight() {
        return (int) (PopupUtils.getWindowHeight(getContext()) * .70f);
    }
}
