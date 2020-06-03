package com.yyxnb.arch.base;

import com.yyxnb.arch.annotations.SwipeStyle;
import com.yyxnb.arch.delegate.ActivityDelegate;

public interface IActivity extends IView {

    default ActivityDelegate getBaseDelegate() {
        return new ActivityDelegate(this);
    }

    default void initWindows() {
    }

    default void setSwipeBack(@SwipeStyle int mSwipeBack) {
    }

}
