package com.yyxnb.arch.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.LruCache;
import android.view.View;

import com.yyxnb.arch.utils.FragmentManagerUtils;
import com.yyxnb.common.AppConfig;

public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    private volatile static FragmentLifecycle lifecycle = null;

    public static FragmentLifecycle getInstance() {
        if (lifecycle == null) {
            synchronized (FragmentLifecycle.class) {
                if (lifecycle == null) {
                    lifecycle = new FragmentLifecycle();
                }
            }
        }
        return lifecycle;
    }

    private LruCache<String, IFragmentDelegate> cache = new LruCache<>(100);

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
//        super.onFragmentAttached(fm, f, context);

        if (f != null) {
            FragmentManagerUtils.getInstance().pushFragment(f);
        }

        if (f instanceof IFragment) {
            IFragmentDelegate fragmentDelegate = fetchFragmentDelegate(f, fm);
            fragmentDelegate.onAttached(context);
        } else {
            AppConfig.getInstance().log(" no IFragment ");
        }
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
//        super.onFragmentCreated(fm, f, savedInstanceState);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onCreated(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
//        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onViewCreated(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
//        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentStarted(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onStarted();
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentResumed(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onResumed();
        }
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentPaused(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onPaused();
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentStopped(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onStoped();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
//        super.onFragmentSaveInstanceState(fm, f, outState);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentViewDestroyed(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onViewDestroyed();
        }
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentDestroyed(fm, f);
        if (f != null) {
            FragmentManagerUtils.getInstance().killFragment(f);
        }
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onDestroyed();
        }
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
//        super.onFragmentDetached(fm, f);
        if (fetchFragmentDelegateFromCache(f) != null) {
            fetchFragmentDelegateFromCache(f).onDetached();
        }
    }

    private IFragmentDelegate fetchFragmentDelegate(Fragment fragment, FragmentManager manager) {

        IFragmentDelegate fragmentDelegate = null;

        if (fetchFragmentDelegateFromCache(fragment) != null) {
            fragmentDelegate = fetchFragmentDelegateFromCache(fragment);
        } else {
            fragmentDelegate = newDelegate(manager, fragment);
        }

        cache.put(getKey(fragment), fragmentDelegate);
        return fragmentDelegate;
    }

    private IFragmentDelegate fetchFragmentDelegateFromCache(Fragment fragment) {

        if (fragment instanceof IFragment) {
            return cache.get(getKey(fragment));
        }
        return null;
    }

    private IFragmentDelegate newDelegate(FragmentManager manager, Fragment fragment) {

        return new FragmentDelegateImpl(fragment, manager);
    }

    private String getKey(Fragment fragment) {
        return fragment.getClass().getName() + fragment.hashCode();
    }

}
