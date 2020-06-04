package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yyxnb.arch.base.IActivity;

import java.io.Serializable;
import java.util.Objects;

public class ActivityDelegate implements Serializable {

    public ActivityDelegate(IActivity iActivity) {
        this.iActivity = iActivity;
        this.mActivity = (Activity) iActivity;
    }

    private IActivity iActivity;
    private Activity mActivity;

    /**
     * 是否第一次加载
     */
    private boolean mIsFirstVisible = true;


    public void onCreate(@Nullable Bundle savedInstanceState) {
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
