package com.yyxnb.arch.base;

import com.yyxnb.arch.annotations.SwipeStyle;
import com.yyxnb.arch.delegate.ActivityDelegate;

public interface IActivity extends IView {

    default ActivityDelegate getBaseDelegate() {
        return null;
    }

    default void initWindows() {
    }

//    default boolean initArgs(Bundle extras) {
//        return true;
//    }

    default void setSwipeBack(@SwipeStyle int mSwipeBack) {
    }

}
