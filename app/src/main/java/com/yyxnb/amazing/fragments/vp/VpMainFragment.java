package com.yyxnb.amazing.fragments.vp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.log.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class VpMainFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = {"1111", "2222", "3333"};

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_vp_main;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_vp_main, container, false);
//    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.page);
//        tabLayout = getView().findViewById(R.id.tab_layout);
//        viewPager = getView().findViewById(R.id.page);

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                if (i == 0) {
                    return Vp1Fragment.newInstance();
                } else if (i == 1) {
                    return Vp2Fragment.newInstance();
                } else if (i == 2) {
                    return Vp3Fragment.newInstance();
                }
                return Vp1Fragment.newInstance();
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        getBaseDelegate().setUserVisibleHint(isVisibleToUser);
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        getBaseDelegate().onHiddenChanged(hidden);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        getBaseDelegate().onConfigurationChanged(newConfig);
//    }

    @Override
    public void initViewData() {
        LogUtils.w("initViewData m: " + hashCode());
    }

    @Override
    public void onVisible() {
        LogUtils.w("onVisible m: " + hashCode());
    }

    @Override
    public void onInVisible() {
        LogUtils.w("onInVisible m: " + hashCode());
    }
}