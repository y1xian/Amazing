package com.yyxnb.amazing.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.yyxnb.adapter.BasePagedAdapter;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.amazing.R;
import com.yyxnb.amazing.bean.MainBean;



public class MainListAdapter extends BasePagedAdapter<MainBean> {

    public MainListAdapter() {
        super(R.layout.item_main_list_layout, new DiffUtil.ItemCallback<MainBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull MainBean mainBean, @NonNull MainBean t1) {
                return mainBean.id == t1.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull MainBean mainBean, @NonNull MainBean t1) {
                return mainBean.equals(t1);
            }
        });
    }

    @Override
    protected void bind( BaseViewHolder holder, MainBean s, int position) {
        String url = "http://img0.imgtn.bdimg.com/it/u=4073821464,3431246218&fm=26&gp=0.jpg";

        holder.setText(R.id.tvText, s.content);
//        ImageHelper.INSTANCE.load(url).into(holder.getView(R.id.ivPic));
    }
}
