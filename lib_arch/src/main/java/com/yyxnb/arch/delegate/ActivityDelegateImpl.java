package com.yyxnb.arch.delegate;

import android.app.Activity;
import android.os.Bundle;

public class ActivityDelegateImpl implements IActivityDelegate {

    private Activity activity = null;
    private IActivity iActivity = null;

    public ActivityDelegateImpl(Activity activity) {
        this.activity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iActivity != null) {

            iActivity.initWidows();

            if (iActivity.initArgs(activity.getIntent().getExtras())) {
                activity.setContentView(iActivity.getLayoutId());
                iActivity.initView();
                iActivity.initData();
            } else {
//                activity.finish();
            }

        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onDestroy() {
        this.activity = null;
        this.iActivity = null;
    }
}
