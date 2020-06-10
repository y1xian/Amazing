package com.yyxnb.amazing.fragments;


import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypePagedAdapter;
import com.yyxnb.amazing.TestActivity;
import com.yyxnb.amazing.TestBaseActivity;
import com.yyxnb.amazing.adapter.MainListAdapter;
import com.yyxnb.amazing.bean.MainBean;
import com.yyxnb.amazing.vm.MainViewModel;
import com.yyxnb.arch.annotations.BindRes;

/**
 * 主页.
 */
@BindRes
public class MainFragment extends AbsListFragment<MainBean, MainViewModel> {

    private MainListAdapter mAdapter = new MainListAdapter();

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRefreshLayout.setEnableRefresh(false).setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new MultiItemTypePagedAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                setMenu(mAdapter.getData().get(position).id);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mAdapter.setSpanSizeLookup((gridLayoutManager, position) -> {
            if (mAdapter.getData().get(position).type == 1) {
                return 2;
            }
            return 1;
        });
        mRecyclerView.setLayoutManager(manager);
        decoration.setDividerWidth(5);
        decoration.setDividerHeight(5);
//        decoration.setDrawBorderTopAndBottom(true);
//        decoration.setDrawBorderLeftAndRight(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addHeaderView(view("头部啊"));
        mAdapter.addFooterView(view("底部啊"));
    }

    private TextView view(String s) {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = 200;
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(s);
        return textView;
    }

    private void setMenu(int position) {
        switch (position) {
            case 11:
                startActivity(new Intent(getContext(), TestBaseActivity.class));
                break;
            case 12:
                startActivity(new Intent(getContext(), TestActivity.class));
                break;
            case 21:
                startFragment(new TestBaseFragment());
                break;
            case 22:
                startFragment(new TestFragment());
                break;
            case 23:
                startFragment(new VpMainFragment());
                break;
            case 31:
                startFragment(NetWorkFragment.newInstance());
                break;
            case 41:
//                startFragment(TitleFragment.newInstance());
                break;
//            case 42:
//                startFragment(new DialogFragment());
//                break;
//            case 43:
//                startFragment(new TagFragment());
//                break;
//            case 4:
//                startFragment(new BehaviorFragment());
//                break;
            default:
                break;
        }
    }

    @Override
    public PagedListAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

}
