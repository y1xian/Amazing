package com.yyxnb.arch.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.utils.ActivityManagerUtils;

public class ActivityDelegate {

    public ActivityDelegate(IActivity iActivity) {
        if (!(iActivity instanceof AppCompatActivity)){
            throw new IllegalArgumentException("Activity请实现IActivity接口");
        }
        this.iActivity = iActivity;
        this.mActivity = (AppCompatActivity) iActivity;
    }

    private IActivity iActivity;
    private AppCompatActivity mActivity;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManagerUtils.getInstance().pushActivity(mActivity);
    }

    public void onDestroy() {
        ActivityManagerUtils.getInstance().killActivity(mActivity);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v       View
     * @param event 事件
     * @return boolean
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

}
