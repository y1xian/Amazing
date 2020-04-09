package com.yyxnb.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public abstract class BasePagedAdapter<T> extends MultiItemTypePagedAdapter<T> {

    public BasePagedAdapter(int mLayoutId, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mLayoutId = mLayoutId;
    }

    private int mLayoutId;

    {
        //noinspection InfiniteRecursion
        addItemDelegate(new ItemDelegate<T>() {
            @Override
            public int layoutId() {
                return mLayoutId;
            }

            @Override
            public boolean isThisType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                bind(holder, t, position);
            }
        });
    }

    protected abstract void bind(BaseViewHolder holder, T t, int position);

}
