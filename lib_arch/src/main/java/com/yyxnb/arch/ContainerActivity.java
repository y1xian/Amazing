package com.yyxnb.arch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.yyxnb.arch.base.BaseActivity;
import com.yyxnb.arch.base.IFragment;
import com.yyxnb.arch.common.ArchConfig;
import com.yyxnb.common.AppConfig;

/**
 * Description: 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 *
 * @author : yyx
 * @date ：2018/6/9
 */
public class ContainerActivity extends BaseActivity {

    @Override
    public int initLayoutResId() {
        return R.layout.base_nav_content;
    }

    public Fragment initBaseFragment() {
        return null;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void initView(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {
            Intent intent = getIntent();

            if (intent == null) {
                throw new RuntimeException("you must provide a page info to display");
            }

            if (initBaseFragment() != null) {
                if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                    initBaseFragment().setArguments(intent.getBundleExtra(ArchConfig.BUNDLE));
                }
                setRootFragment((IFragment) initBaseFragment(), R.id.fragmentContent);
                return;
            }

            String fragmentName = intent.getStringExtra(ArchConfig.FRAGMENT);
            if (fragmentName.isEmpty()) {
                throw new IllegalArgumentException("can not find page fragmentName");
            }

            Class<?> fragmentClass = Class.forName(fragmentName);

            Fragment fragment = (Fragment) fragmentClass.newInstance();

            if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                fragment.setArguments(intent.getBundleExtra(ArchConfig.BUNDLE));
            }

            setRootFragment((IFragment) fragment, R.id.fragmentContent);

        } catch (Exception e) {
            e.printStackTrace();
            AppConfig.getInstance().log(e.getMessage());
        }
    }
}
