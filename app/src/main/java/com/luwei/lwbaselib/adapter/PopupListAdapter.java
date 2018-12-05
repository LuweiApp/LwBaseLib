package com.luwei.lwbaselib.adapter;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.lwbaselib.R;

/**
 * Created by LiCheng
 * Date：2018/11/15
 */
public class PopupListAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_popup_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        for (int i = 0; i < 5; i++) {
            viewHolder.btnPopupList.setText("Button" + position);
            viewHolder.tvPopupList.setText("List" + position);
            viewHolder.btnPopupList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPopupList;
        Button btnPopupList;

        private ViewHolder(View itemView) {
            super(itemView);
            btnPopupList = itemView.findViewById(R.id.btn_popup_list);
            tvPopupList = itemView.findViewById(R.id.tv_popup_list);
        }
    }

}
