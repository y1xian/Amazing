package com.yyxnb.amazing.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.yyxnb.amazing.R;
import com.yyxnb.arch.annotations.BindRes;
import com.yyxnb.arch.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部show/hide
 */
@BindRes(layoutRes = R.layout.fragment_bottom)
public class BottomFragment extends BaseFragment implements View.OnClickListener {

    private RadioButton rb_1, rb_2, rb_3;
    private FragmentManager manager;
    private List<Fragment> fragments;
    private int current = -1;

    @Override
    public void initView(Bundle savedInstanceState) {
        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);

        rb_1.setOnClickListener(this);
        rb_2.setOnClickListener(this);
        rb_3.setOnClickListener(this);
    }

    @Override
    public void initViewData() {
        manager = getChildFragmentManager();

        if (fragments == null) {
            fragments = new ArrayList<>();
            fragments.add(new VpMainFragment());
            fragments.add(new TitleFragment());
            fragments.add(new TagFragment());
        }

        showFragment(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_1:
                showFragment(0);
                break;
            case R.id.rb_2:
                showFragment(1);
                break;
            case R.id.rb_3:
                showFragment(2);
                break;
            default:
                break;
        }
    }

    private void showFragment(int index) {
        if (current == index) {
            return;
        }
        final FragmentTransaction transaction = manager.beginTransaction();
        if (!fragments.get(index).isAdded()) {
            transaction.add(R.id.mFrameLayout, fragments.get(index));
        }
        if (current != -1) {
            transaction.hide(fragments.get(current));
        }
        current = index;
        transaction.show(fragments.get(current)).commit();
    }
}