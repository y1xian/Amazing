package com.yyxnb.amazing;

import com.yyxnb.arch.ContainerActivity;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.amazing.fragments.HomeFragment;

public class MainActivity extends ContainerActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        startFragment(new TitleFragment());
//    }

    @Override
    public BaseFragment initBaseFragment() {
        return new HomeFragment();
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