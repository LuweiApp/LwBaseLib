package com.luwei.lwbaselib.module.recyclerview.decoration;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luwei.lwbaselib.R;

import java.util.List;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/5
 */
public class StaggeredAdapter extends SampleAdapter {

    public StaggeredAdapter(List<String> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sample, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        viewHolder.itemView.setBackgroundColor(0xFF7AD3D7);
        if (i % 2 == 0) {
            viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        } else {
           viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        }
//        if (i % 2 == 0) {
//            viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT));
//        } else {
//            viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(500,ViewGroup.LayoutParams.MATCH_PARENT));
//        }
    }
}
