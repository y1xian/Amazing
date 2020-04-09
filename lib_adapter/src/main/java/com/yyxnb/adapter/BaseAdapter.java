package com.yyxnb.adapter;

public abstract class BaseAdapter<T> extends MultiItemTypeAdapter<T> {

    public BaseAdapter(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    protected int mLayoutId;

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
