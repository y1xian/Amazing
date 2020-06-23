package com.yyxnb.amazing;

import android.support.v4.app.Fragment;

import com.yyxnb.amazing.fragments.MainFragment;
import com.yyxnb.amazing.base.ContainerActivity;

public class MainActivity extends ContainerActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        startFragment(new TitleFragment());
//    }

    @Override
    public Fragment initBaseFragment() {
        return new MainFragment();
    }


//    @Override
//    public int initLayoutResId() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public void initView(Bundle savedInstanceState) {
//        startFragment(new TitleFragment());
//    }
}
