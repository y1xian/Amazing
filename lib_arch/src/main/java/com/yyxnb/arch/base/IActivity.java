package com.yyxnb.arch.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yyxnb.arch.annotations.SwipeStyle;
import com.yyxnb.arch.delegate.ActivityDelegate;
import com.yyxnb.arch.utils.DelegateUtils;

public interface IActivity extends IView {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    default ActivityDelegate getBaseDelegate() {
        ActivityDelegate delegate = DelegateUtils.getInstance().getActivityDelegates().get(hashCode());
        if (delegate == null) {
            delegate = new ActivityDelegate(this);
            DelegateUtils.getInstance().getActivityDelegates().put(hashCode(), delegate);
        }
        return delegate;
    }

    default void initWindows() {
    }

    default void setSwipeBack(@SwipeStyle int mSwipeBack) {
    }

}
