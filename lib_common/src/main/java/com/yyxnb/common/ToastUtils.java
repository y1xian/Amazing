package com.yyxnb.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * 自定义Toast
 *
 * @author yyx
 */
public class ToastUtils implements Serializable {
    private static final @ColorInt
    int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private static final @ColorInt
    int ERROR_COLOR = Color.parseColor("#D8524E");
    private static final @ColorInt
    int INFO_COLOR = Color.parseColor("#3278B5");
    private static final @ColorInt
    int SUCCESS_COLOR = Color.parseColor("#5BB75B");
    private static final @ColorInt
    int WARNING_COLOR = Color.parseColor("#FB9B4D");
    private static final @ColorInt
    int NORMAL_COLOR = Color.parseColor("#444344");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    private static Context context = AppConfig.getInstance().getContext();

    /**
     * 上次显示的内容
     */
    private static String oldMsg;
    /**
     * 上次时间
     */
    private static long oldTime = 0;
    /**
     * Toast对象
     */
    private static Toast mToast = null;

    private ToastUtils() {
    }

    public static Toast normal(@NonNull String message) {
        return normal(message, Toast.LENGTH_SHORT, null);
    }

    public static Toast normal(@NonNull String message, Drawable icon) {
        return normal(message, Toast.LENGTH_SHORT, icon);
    }

    public static Toast normal(@NonNull String message, int duration) {
        return normal(message, duration, null);
    }

    public static Toast normal(@NonNull String message, int duration,
                               Drawable icon) {
        return custom(message, icon, NORMAL_COLOR, duration);
    }

    public static Toast info(@NonNull String message) {
        return custom(message, null, INFO_COLOR);
    }

    public static Toast warning(@NonNull String message) {
        return custom(message, null, WARNING_COLOR);
    }

    public static Toast success(@NonNull String message) {
        return custom(message, null, SUCCESS_COLOR);
    }

    public static Toast error(@NonNull String message) {
        return custom(message, null, ERROR_COLOR);
    }

    public static Toast custom(@NonNull String message, @ColorInt int tintColor) {
        return custom(message, null, DEFAULT_TEXT_COLOR, tintColor, Toast.LENGTH_SHORT);
    }

    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int tintColor) {
        return custom(message, icon, DEFAULT_TEXT_COLOR, tintColor, Toast.LENGTH_SHORT);
    }

    public static Toast custom(@NonNull String message, @ColorInt int tintColor, int duration) {
        return custom(message, null, DEFAULT_TEXT_COLOR, tintColor, duration);
    }

    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int tintColor, int duration) {
        return custom(message, icon, DEFAULT_TEXT_COLOR, tintColor, duration);
    }

    public static Toast custom(@NonNull String message, @DrawableRes int iconRes,
                               @ColorInt int textColor, @ColorInt int tintColor, int duration) {
        return custom(message, getDrawable(context, iconRes), textColor, tintColor, duration);
    }

    /**
     * 自定义toast方法
     *
     * @param message   提示消息文本
     * @param icon      提示消息的icon,传入null代表不显示
     * @param textColor 提示消息文本颜色
     * @param tintColor 提示背景颜色
     * @param duration  显示时长
     * @return
     */
    public static Toast custom(@NonNull String message, Drawable icon, @ColorInt int textColor, int tintColor, int duration) {

        if (mToast == null) {
            mToast = new Toast(context);
        } else {
            mToast.cancel();
            mToast = null;
            mToast = new Toast(context);
        }

        @SuppressLint("InflateParams")
        View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        TextView toastText = toastLayout.findViewById(R.id.toast_text);

        if (tintColor != -1) {
            setBackground(toastLayout, getDrawable(context, tintColor));
        }

        if (icon == null) {
            toastIcon.setVisibility(View.GONE);
        } else {
            setBackground(toastIcon, icon);
        }

        toastText.setTextColor(textColor);
        toastText.setText(message);
        toastText.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        mToast.setView(toastLayout);
        mToast.setDuration(duration);
        return mToast;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}