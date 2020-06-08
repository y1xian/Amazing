package com.yyxnb.arch.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yyxnb.arch.annotations.SwipeStyle;
import com.yyxnb.arch.delegate.ActivityDelegate;
import com.yyxnb.arch.utils.AppManager;

public interface IActivity extends IView {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default ActivityDelegate getBaseDelegate() {
        ActivityDelegate delegate = AppManager.getInstance().getActivityDelegates().get(hashCode());
        if (delegate == null) {
            delegate = new ActivityDelegate(this);
            AppManager.getInstance().getActivityDelegates().put(hashCode(), delegate);
        }
        return delegate;
    }

    default void initWindows() {
    }

    default void setSwipeBack(@SwipeStyle int mSwipeBack) {
    }

}
