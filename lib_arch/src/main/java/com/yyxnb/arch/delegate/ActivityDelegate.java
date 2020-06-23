package com.yyxnb.arch.delegate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.common.ArchConfig;
import com.yyxnb.arch.common.Bus;
import com.yyxnb.arch.common.MsgEvent;
import com.yyxnb.common.MainThreadUtils;
import com.yyxnb.common.StatusBarUtils;

import java.io.Serializable;
import java.util.Objects;

public class ActivityDelegate implements Serializable {

    public ActivityDelegate(IActivity iActivity) {
        this.iActivity = iActivity;
        this.mActivity = (FragmentActivity) iActivity;
    }

    private IActivity iActivity;
    private FragmentActivity mActivity;

    private int layoutRes = 0;
    private boolean statusBarTranslucent = ArchConfig.statusBarTranslucent;
    private boolean fitsSystemWindows = ArchConfig.fitsSystemWindows;
    private int statusBarColor = ArchConfig.statusBarColor;
    private int statusBarDarkTheme = ArchConfig.statusBarStyle;
    private boolean needLogin;
    private boolean isExtends;
    private boolean isContainer;

    /**
     * 是否第一次加载
     */
    private boolean mIsFirstVisible = true;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        initAttributes();
        if (!isExtends) {
            if (layoutRes != 0 || iActivity.initLayoutResId() != 0) {
                mActivity.setContentView(layoutRes == 0 ? iActivity.initLayoutResId() : layoutRes);
            }
        }
        initView();
    }

    private void initView() {
        if (!isContainer) {
            // 不留空间 则透明
            if (!fitsSystemWindows) {
                StatusBarUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
            } else {
                StatusBarUtils.setStatusBarColor(getWindow(), statusBarColor);
            }
            StatusBarUtils.setStatusBarStyle(getWindow(), statusBarDarkTheme == BarStyle.DarkContent);
            StatusBarUtils.setStatusBarTranslucent(getWindow(), statusBarTranslucent, fitsSystemWindows);
        }

    }

    private Window getWindow() {
        return mActivity.getWindow();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (mIsFirstVisible && hasFocus) {
            mIsFirstVisible = false;
            iActivity.initViewData();
            iActivity.initObservable();
        }
    }

    public void onDestroy() {
        mIsFirstVisible = true;
        iActivity = null;
        mActivity = null;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    @SuppressWarnings("AlibabaAvoidNegationOperator")
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 加载注解设置
     */
    public void initAttributes() {
        MainThreadUtils.post(() -> {
            final BindRes bindRes = iActivity.getClass().getAnnotation(BindRes.class);
            if (bindRes != null) {
                layoutRes = bindRes.layoutRes();
                fitsSystemWindows = bindRes.fitsSystemWindows();
                statusBarTranslucent = bindRes.statusBarTranslucent();
                if (bindRes.statusBarStyle() != BarStyle.None) {
                    statusBarDarkTheme = bindRes.statusBarStyle();
                }
                if (bindRes.statusBarColor() != 0) {
                    statusBarColor = bindRes.statusBarColor();
                }
                needLogin = bindRes.needLogin();
                isExtends = bindRes.isExtends();
                isContainer = bindRes.isContainer();
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
                    Bus.post(new MsgEvent(ArchConfig.NEED_LOGIN_CODE));
                }
            }
        });
    }

    public void setSwipeBack(int mSwipeBack) {
//        final ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(mActivity, true);
//        switch (mSwipeBack) {
//            case SwipeStyle.Full:
//                ParallaxHelper.enableParallaxBack(mActivity);
//                //全屏滑动
//                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL);
//                break;
//            case SwipeStyle.Edge:
//                ParallaxHelper.enableParallaxBack(mActivity);
//                //边缘滑动
//                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT);
//                break;
//            case SwipeStyle.None:
//                ParallaxHelper.disableParallaxBack(mActivity);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityDelegate that = (ActivityDelegate) o;
        return iActivity.equals(that.iActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iActivity);
    }
}
