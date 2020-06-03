package com.yyxnb.arch.delegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yyxnb.arch.annotations.BarStyle;
import com.yyxnb.arch.annotations.BindFragment;
import com.yyxnb.arch.annotations.SwipeStyle;
import com.yyxnb.arch.base.IActivity;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.arch.common.ArchConfig;
import com.yyxnb.common.MainThreadUtils;
import com.yyxnb.common.StatusBarUtils;
import com.yyxnb.common.log.LogUtils;

import java.io.Serializable;
import java.util.Objects;

public class FragmentDelegate implements Serializable {

    public FragmentDelegate(IFragment iFragment) {
        this.iFragment = iFragment;
        this.mFragment = (Fragment) iFragment;
        mLazyDelegate = new FragmentLazyDelegate(mFragment);
    }

    private IFragment iFragment;
    private IActivity iActivity;

    private View mRootView;

    private Fragment mFragment;
    private AppCompatActivity mActivity;

    private FragmentLazyDelegate mLazyDelegate;

    private int layoutRes = 0;
    private boolean statusBarTranslucent = ArchConfig.statusBarTranslucent;
    private boolean fitsSystemWindows = ArchConfig.fitsSystemWindows;
    private boolean statusBarHidden = ArchConfig.statusBarHidden;
    private int statusBarColor = ArchConfig.statusBarColor;
    private int statusBarDarkTheme = ArchConfig.statusBarStyle;
    private int swipeBack = SwipeStyle.Edge;
    private boolean subPage = false;
    private int group = -1;
    private boolean needLogin = false;

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public void onAttach(Context context) {
        mActivity = (AppCompatActivity) context;
        if (!(mActivity instanceof IActivity)) {
            throw new IllegalArgumentException("AppCompatActivity请实现IActivity接口");
        }
        iActivity = (IActivity) mActivity;
        LogUtils.e( "mActivity " + mActivity + " , " + (mActivity == null));
    }

//    public void onAttach(AppCompatActivity activity) {
//
//        if (!(activity instanceof IActivity)) {
//            throw new IllegalArgumentException("AppCompatActivity请实现IActivity接口");
//        }
//        mActivity = activity;
//        iActivity = (IActivity) activity;
//    }

    public void onCreate(Bundle savedInstanceState) {
        mLazyDelegate.onCreate(savedInstanceState);
//        FragmentManagerUtils.getInstance().pushFragment(mFragment);
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initAttributes();
        if (null == mRootView) {
            mRootView = inflater.inflate(layoutRes == 0 ? iFragment.initLayoutResId()
                    : layoutRes, container, false);
        } else {
            //  二次加载删除上一个子view
            ViewGroup viewGroup = (ViewGroup) mRootView;
            viewGroup.removeView(mRootView);
        }
        mRootView.setOnTouchListener((v, event) -> {
            mActivity.onTouchEvent(event);
            return false;
        });
        return mRootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        mLazyDelegate.onActivityCreated(savedInstanceState, subPage);
        iFragment.initView(savedInstanceState);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        mLazyDelegate.setUserVisibleHint(isVisibleToUser);
    }

    public void onHiddenChanged(boolean hidden) {
        mLazyDelegate.onHiddenChanged(hidden);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        mLazyDelegate.onConfigurationChanged(newConfig);
    }

    public void onResume() {
        mLazyDelegate.onResume();
    }

    public void onPause() {
        mLazyDelegate.onPause();
    }

    public void onDestroy() {
        mLazyDelegate.onDestroy();
        iFragment = null;
        iActivity = null;
        mActivity = null;
        mFragment = null;
        mRootView = null;
    }

    public void onDestroyView() {
//        FragmentManagerUtils.getInstance().killFragment(mFragment);
    }

    /**
     * 加载注解设置
     */
    public void initAttributes() {
        MainThreadUtils.post(() -> {
            BindFragment bindFragment = iFragment.getClass().getAnnotation(BindFragment.class);
            if (bindFragment != null) {
                layoutRes = bindFragment.layoutRes();
                fitsSystemWindows = bindFragment.fitsSystemWindows();
                statusBarTranslucent = bindFragment.statusBarTranslucent();
                swipeBack = bindFragment.swipeBack();
                subPage = bindFragment.subPage();
                group = bindFragment.group();
                if (bindFragment.statusBarStyle() != BarStyle.None) {
                    statusBarDarkTheme = bindFragment.statusBarStyle();
                }
                if (bindFragment.statusBarColor() != 0) {
                    statusBarColor = bindFragment.statusBarColor();
                }
                needLogin = bindFragment.needLogin();
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
//                    ArchConfig.NEED_LOGIN.bus(needLogin);
                }
            }
            if (!subPage) {
//                setNeedsStatusBarAppearanceUpdate();
            }
        });

    }

    /**
     * 获得成员变量
     */
//    public void initDeclaredFields() {
//        MainThreadUtils.post(() -> {
//            Field[] declaredFields = iFragment.getClass().getDeclaredFields();
//            for (Field field : declaredFields) {
//                // 允许修改反射属性
//                field.setAccessible(true);
//
//                /**
//                 *  根据 @BindViewModel 注解, 查找注解标示的变量（ViewModel）
//                 *  并且 创建 ViewModel 实例, 注入到变量中
//                 */
//                BindViewModel annotation = field.getAnnotation(BindViewModel.class);
//                if (annotation != null) {
//                    try {
//                        field.set(iFragment, getViewModel(field, annotation.isActivity()));
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    /**
     * 更新状态栏样式
     */
    public void setNeedsStatusBarAppearanceUpdate() {

        if (subPage) {
            return;
        }
        // 侧滑返回
//        iActivity.setSwipeBack(swipeBack);

        // 隐藏
        boolean hidden = statusBarHidden;
        StatusBarUtils.setStatusBarHidden(getWindow(), hidden);

        // 文字颜色
        int statusBarStyle = statusBarDarkTheme;
        StatusBarUtils.setStatusBarStyle(getWindow(), statusBarStyle == BarStyle.DarkContent);

        // 隐藏 or 不留空间 则透明
        if (hidden || !fitsSystemWindows) {
            StatusBarUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        } else {
            int statusBarColor = this.statusBarColor;

            //不为深色
            boolean shouldAdjustForWhiteStatusBar = !StatusBarUtils.isBlackColor(statusBarColor, 176);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && statusBarStyle == BarStyle.LightContent;
            }
            //如果状态栏处于白色且状态栏文字也处于白色，避免看不见
            if (shouldAdjustForWhiteStatusBar) {
                statusBarColor = ArchConfig.shouldAdjustForWhiteStatusBar;
            }

            StatusBarUtils.setStatusBarColor(getWindow(), statusBarColor);
        }
        StatusBarUtils.setStatusBarTranslucent(getWindow(), statusBarTranslucent, fitsSystemWindows);
    }

    public Window getWindow() {
        return mActivity.getWindow();
    }

//    public ViewModel getViewModel(Field field, boolean activity) {
//        if (activity) {
//            return ViewModelFactory.createViewModel(mActivity, field);
//        } else {
//            return ViewModelFactory.createViewModel(mFragment, field);
//        }
//    }

    public Bundle initArguments() {
        Bundle args = mFragment.getArguments();
        if (args == null) {
            args = new Bundle();
            mFragment.setArguments(args);
        }
        return args;
    }

    public <T extends IFragment> void startFragment(T targetFragment, int requestCode) {
        LogUtils.e(" mmmm " + mActivity + " , " + getActivity());
//        Bundle bundle = initArguments();
//        Intent intent = new Intent(mActivity, ContainerActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(ArchConfig.FRAGMENT, targetFragment.getClass().getCanonicalName());
//        bundle.putInt(ArchConfig.REQUEST_CODE, requestCode);
//        intent.putExtra(ArchConfig.BUNDLE, bundle);
//        mActivity.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FragmentDelegate that = (FragmentDelegate) o;
        return iFragment.equals(that.iFragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iFragment);
    }
}
