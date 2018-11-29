package com.luwei.lwbaselib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.luwei.lwbaselib.R;

/**
 * Created by LiCheng
 * Date：2018/11/15
 */
public class PopupListAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_popup_list,null,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        RecyclerHolder viewHolder = (RecyclerHolder) holder;
        for (int j = 0; j <5 ; j++) {
            viewHolder.btnPopupList.setText("Button"+i);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        Button btnPopupList;

        private RecyclerHolder(View itemView) {
            super(itemView);
            btnPopupList = itemView.findViewById(R.id.btn_popup_list);
        }
    }

}
