package com.yyxnb.arch.utils;

import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 管理所有fragment
 */
public final class FragmentManagerUtils implements Serializable {

    private volatile static FragmentManagerUtils fragmentManagerUtils;
    private static Stack<Fragment> fragmentStack;

    public static FragmentManagerUtils getInstance() {
        if (fragmentManagerUtils == null) {
            synchronized (FragmentManagerUtils.class) {
                if (fragmentManagerUtils == null) {
                    fragmentManagerUtils = new FragmentManagerUtils();
                }
            }
        }
        return fragmentManagerUtils;
    }

    public int count() {
        return fragmentStack.size();
    }

    public List<Fragment> getFragmentList() {
        List<Fragment> list = new ArrayList<>();
        if (!fragmentStack.isEmpty()) {
            list.addAll(fragmentStack);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public void pushFragment(Fragment fragment) {
        if (fragmentStack == null) {
            fragmentStack = new Stack();
        }
        fragmentStack.add(fragment);
    }

    public void killFragment(Fragment fragment) {
        if (fragmentStack != null) {
            fragmentStack.remove(fragment);
        }
    }

    public Fragment currentFragment() {
        if (fragmentStack.isEmpty()) {
            return null;
        }
        return fragmentStack.lastElement();
    }

    public Fragment beforeFragment() {
        if (count() < 2) {
            return null;
        }
        return fragmentStack.get(count() - 2);
    }


}
