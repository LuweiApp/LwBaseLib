package com.luwei.lwbaselib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.bean.ImageViewInfo;
import com.luwei.recyclerview.adapter.extension.LwItemBinder;
import com.luwei.recyclerview.adapter.extension.LwViewHolder;
import com.luwei.util.imageloader.ImageLoaderUtils;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/12
 * @Description:
 */
public class ImageBinder extends LwItemBinder<ImageViewInfo> {
    private Context mContext;
    public ImageBinder(Context context){
        this.mContext = context;
    }
    @Override
    protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return inflater.inflate(R.layout.item_image,parent,false);
    }

    @Override
    protected void onBind(@NonNull LwViewHolder holder, @NonNull ImageViewInfo item) {
        ImageLoaderUtils.loadImage(mContext,holder.getView(R.id.image),item.getUrl());
    }
}
