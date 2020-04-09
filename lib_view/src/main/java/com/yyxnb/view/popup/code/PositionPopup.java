package com.yyxnb.view.popup.code;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyxnb.view.R;
import com.yyxnb.view.popup.PartShadowContainer;
import com.yyxnb.view.popup.PopupUtils;
import com.yyxnb.view.popup.animator.PopupAnimator;
import com.yyxnb.view.popup.animator.ScrollScaleAnimator;

import static com.yyxnb.view.popup.animator.PopupAnimation.ScaleAlphaFromCenter;


/**
 * Description: 用于自由定位的弹窗
 */
public abstract class PositionPopup extends BasePopup {

    PartShadowContainer attachPopupContainer;

    public PositionPopup(@NonNull Context context) {
        super(context);
        attachPopupContainer = findViewById(R.id.attachPopupContainer);

        View contentView = LayoutInflater.from(getContext()).inflate(initLayoutResId(), attachPopupContainer, false);
        attachPopupContainer.addView(contentView);
    }

    @Override
    protected int initPopupLayoutId() {
        return R.layout._popup_attach_popup_view;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        PopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), () -> {
            if (popupInfo.isCenterHorizontal) {
                float left = (PopupUtils.getWindowWidth(getContext()) - attachPopupContainer.getMeasuredWidth()) / 2f;
                attachPopupContainer.setTranslationX(left);
            } else {
                attachPopupContainer.setTranslationX(popupInfo.offsetX);
            }
            attachPopupContainer.setTranslationY(popupInfo.offsetY);
        });
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return new ScrollScaleAnimator(getPopupContentView(), ScaleAlphaFromCenter);
    }
}
