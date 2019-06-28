package com.luwei.lwbaselib.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.bean.MainNaviBean;
import com.luwei.recyclerview.adapter.extension.LwItemBinder;
import com.luwei.recyclerview.adapter.extension.LwViewHolder;

/**
 * @author LiCheng
 * @date 2019-06-27
 * 首页导航RecycleView的Binder
 */
public class MainNaviBinder extends LwItemBinder<MainNaviBean> {

    @Override
    protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        //绑定item布局
        return inflater.inflate(R.layout.item_main_navi, parent, false);
    }

    @Override
    protected void onBind(@NonNull LwViewHolder holder, @NonNull MainNaviBean item) {
        //设置标题
        holder.setText(R.id.tv_title, item.getTitle());
        //点击跳转
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.getJumper().jump(holder.itemView.getContext(), item);
            }
        });
    }
}
