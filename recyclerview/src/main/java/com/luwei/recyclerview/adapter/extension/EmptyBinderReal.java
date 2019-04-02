package com.luwei.recyclerview.adapter.extension;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyBinderReal extends LwItemBinder<EmptyBean> {

    private EmptyBinder mEmptyBinder;
    private View mView;

    public EmptyBinderReal(EmptyBinder emptyBinder) {
        mEmptyBinder = emptyBinder;
    }

    @Override
    protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        if (mView == null && mEmptyBinder != null) {
            mView = mEmptyBinder.getView(inflater, parent);
        }
        return mView;
    }

    @Override
    protected void onBind(@NonNull LwViewHolder holder, @NonNull EmptyBean item) {

    }
}
