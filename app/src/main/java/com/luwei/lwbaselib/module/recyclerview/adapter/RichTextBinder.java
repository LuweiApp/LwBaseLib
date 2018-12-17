package com.luwei.lwbaselib.module.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luwei.lwbaselib.R;
import com.luwei.recyclerview.adapter.extension.LwItemBinder;
import com.luwei.recyclerview.adapter.extension.LwViewHolder;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/15
 */
public class RichTextBinder extends LwItemBinder<RichTextBean> {
    @Override
    protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return inflater.inflate(R.layout.item_rich_text,parent,false);
    }

    @Override
    protected void onBind(@NonNull LwViewHolder holder, @NonNull RichTextBean item) {
        holder.setImageResource(R.id.iv_img,item.getImgRes());
        holder.setText(R.id.tv_content,item.getContent());
    }
}
