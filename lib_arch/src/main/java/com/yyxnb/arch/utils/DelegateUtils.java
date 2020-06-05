package com.yyxnb.arch.utils;

import com.yyxnb.arch.delegate.ActivityDelegate;
import com.yyxnb.arch.delegate.FragmentDelegate;

import java.util.LinkedHashMap;

/**
 * 管理代理
 */
public class DelegateUtils {

    private volatile static DelegateUtils delegateUtils;

    public static DelegateUtils getInstance() {
        if (delegateUtils == null) {
            synchronized (DelegateUtils.class) {
                if (delegateUtils == null) {
                    delegateUtils = new DelegateUtils();
                }
            }
        }
        return delegateUtils;
    }

    private volatile LinkedHashMap<Integer, ActivityDelegate> activityDelegates;
    private volatile LinkedHashMap<Integer, FragmentDelegate> fragmentDelegates;

    public LinkedHashMap<Integer, ActivityDelegate> getActivityDelegates() {
        if (activityDelegates == null) {
            synchronized (DelegateUtils.class) {
                if (activityDelegates == null) {
                    activityDelegates = new LinkedHashMap<>();
                }
            }
        }
        return activityDelegates;
    }

    public LinkedHashMap<Integer, FragmentDelegate> getFragmentDelegates() {
        if (fragmentDelegates == null) {
            synchronized (DelegateUtils.class) {
                if (fragmentDelegates == null) {
                    fragmentDelegates = new LinkedHashMap<>();
                }
            }
        }
        return fragmentDelegates;
    }
}
