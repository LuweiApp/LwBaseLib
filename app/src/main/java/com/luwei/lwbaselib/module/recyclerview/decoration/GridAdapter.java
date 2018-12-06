package com.luwei.lwbaselib.module.recyclerview.decoration;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/30
 */
public class GridAdapter extends SampleAdapter {

    private int layoutId;

    public GridAdapter(List<String> dataList, @LayoutRes int layoutId) {
        super(dataList);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layoutId, viewGroup, false);
        return new ViewHolder(view);
    }
}
