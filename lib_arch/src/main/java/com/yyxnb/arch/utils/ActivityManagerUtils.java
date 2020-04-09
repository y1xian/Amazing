package com.yyxnb.arch.utils;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class ActivityManagerUtils implements Serializable {

    private volatile static ActivityManagerUtils activityManagerUtils;
    private static Stack<Activity> activityStack;

    public static ActivityManagerUtils getInstance() {
        if (activityManagerUtils == null) {
            synchronized (ActivityManagerUtils.class) {
                if (activityManagerUtils == null) {
                    activityManagerUtils = new ActivityManagerUtils();
                }
            }
        }
        return activityManagerUtils;
    }

    public int count() {
        return activityStack.size();
    }

    public List<Activity> getActivityList() {
        List<Activity> list = new ArrayList<>();
        if (!activityStack.isEmpty()) {
            list.addAll(activityStack);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    public void killActivity(Activity activity) {
        if (activityStack != null) {
//            activity.finish();
            activityStack.remove(activity);
        }
    }

    public Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    public Activity beforeActivity() {
        if (count() < 2) {
            return null;
        }
        return activityStack.get(count() - 2);
    }

}
