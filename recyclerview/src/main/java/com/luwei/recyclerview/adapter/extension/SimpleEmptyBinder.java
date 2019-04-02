package com.luwei.recyclerview.adapter.extension;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleEmptyBinder implements EmptyBinder {
    @Override
    public View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText("暂无数据");
        return textView;
    }
}
