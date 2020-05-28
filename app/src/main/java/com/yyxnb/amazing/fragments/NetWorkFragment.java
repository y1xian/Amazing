package com.yyxnb.amazing.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.amazing.R;
import com.yyxnb.amazing.adapter.NetWorkListAdapter;
import com.yyxnb.amazing.vm.NetWorkViewModel;
import com.yyxnb.arch.annotations.BindFragment;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.common.AppConfig;
import com.yyxnb.common.log.LogUtils;


/**
 * 网络列表.
 */
@BindFragment(layoutRes = R.layout.fragment_net_work)
public class NetWorkFragment extends BaseFragment/*VM<NetWorkViewModel>*/ {

    @BindViewModel
    NetWorkViewModel mViewModel;
//    @BindViewModel
//    MsgViewModel msgViewModel;

    private NetWorkListAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int page = 1;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mRefreshLayout = findViewById(R.id.mRefreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NetWorkListAdapter();
//        mAdapter = new RecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);

//        View view = LayoutInflater.from(mContext).inflate(R.layout._loading_layout_empty, (ViewGroup) getMRootView(), false);
//        mAdapter.setEmptyView(view);
//        mAdapter.setEmptyView(R.layout._loading_layout_empty);
//        mAdapter.addFootView(view);


        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mAdapter.setDataItems(null);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mViewModel.reqTeam();
            }
        });

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
//            @Override
////            public void onItemClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position) {
////                ToastUtils.INSTANCE.normal("" + position);
////            }


            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);

            }

            @Override
            public void onItemChildClick(View view, BaseViewHolder holder, int position) {
                super.onItemChildClick(view, holder, position);
                if (view.getId() == R.id.mLinearLayout) {
//                    msgViewModel.reqToast("第 " + position);
                }
            }
        });


    }

    @Override
    public void initViewData() {
        super.initViewData();

        AppConfig.getInstance().log(" initViewData ");

        mViewModel.reqTeam2();

        mViewModel.getList3();

//        mViewModel.getList().observe(this,data->{
////            AppConfig.getInstance().log(" getList " + data.getResult().list.size());
//            LogUtils.list(data.getResult().list);
//            mAdapter.setDataItems(data.getResult().list);
//        });

//        LogUtils.INSTANCE.e(CacheManager.cacheSize() + " 条");

//        mViewModel.getTestList().observe(this, t -> {
//            switch (t.status) {
//                case SUCCESS:
////                    page++;
//                    mRefreshLayout.finishRefresh();
//                    if (t.data != null) {
//                        AppConfig.getInstance().log(" success   " + (t.data.getData().list.size()));
////                        LogUtils.INSTANCE.list(t.data.getData());
//                        mAdapter.setDataItems(t.data.getData().list);
//                    }
//                    break;
//                case ERROR:
//                    AppConfig.getInstance().log(" ERROR ");
//                    break;
//                case LOADING:
////                    if (t.data != null) {
////                        AppConfig.getInstance().log(" loading   " + (t.data.getList().size() > 0));
////                        mAdapter.setDataItems(t.data.getList());
////                    }
//                    break;
//            }
//        });
//        mViewModel.getTestList().observe(this, t -> {
//            switch (t.status) {
//                case SUCCESS:
////                    page++;
//                    mRefreshLayout.finishRefresh();
//                    if (t.data != null) {
//                        AppConfig.getInstance().log(" SUCCESS   " + (t.data.getResult().list.size()));
////                        LogUtils.INSTANCE.list(t.data.getData());
//                        mAdapter.setDataItems(t.data.getResult().list);
//                    }
//                    break;
//                case ERROR:
//                    AppConfig.getInstance().log(" ERROR ");
//                    break;
//                case LOADING:
//                    AppConfig.getInstance().log(" LOADING ");
////                    if (t.data != null) {
////                        AppConfig.getInstance().log(" loading   " + (t.data.getData().list.size() > 0));
////                        mAdapter.setDataItems(t.data.getData().list);
////                    }
//                    break;
//            }
//        });


        mViewModel.result.observe(this, t -> {
//                    page++;
            mRefreshLayout.finishRefresh();
            if (t != null ) {
                AppConfig.getInstance().log(" SUCCESS   " + (t.list.size()));
                //                        LogUtils.INSTANCE.list(t.data.getData());
                mAdapter.setDataItems(t.list);
            }
        });
//        mViewModel.result.observe(this, t -> {
////                    page++;
//            mRefreshLayout.finishRefresh();
//            if (t != null && t.list != null) {
//                AppConfig.getInstance().log(" SUCCESS   " + (t.list.size()));
//                //                        LogUtils.INSTANCE.list(t.data.getData());
//                mAdapter.setDataItems(t.list);
//            }
//        });
        mViewModel.result2.observe(this, t -> {
//                    page++;
            mRefreshLayout.finishRefresh();
            if (t != null && t.list != null) {
                AppConfig.getInstance().log(" SUCCESS1   " + (t.list.size()));
                //                        LogUtils.INSTANCE.list(t.data.getData());
                mAdapter.setDataItems(t.list);
            }
        });

    }

    public static NetWorkFragment newInstance() {

        Bundle args = new Bundle();

        NetWorkFragment fragment = new NetWorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
//        CacheManager.deleteKey("getTestList");
        super.onDestroy();
    }
}
