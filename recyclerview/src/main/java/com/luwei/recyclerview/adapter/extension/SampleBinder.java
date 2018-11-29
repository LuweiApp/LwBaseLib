package com.luwei.recyclerview.adapter.extension;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/15
 */
public class SampleBinder extends LwItemBinder<Object> {

    public static final int DEFAULT_TEXT_SIZE = 15; //sp
    public static final int DEFAULT_HEIGHT = 50;  //dp
    public static final int DEFAULT_PADDING_HORIZONTAL = 6; //dp
    public static final int DEFAULT_PADDING_VERTICAL = 4; //dp

    @Override
    protected View getView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float density = metrics.density;
        int heightPx = dp2px(density, DEFAULT_HEIGHT);
        int paddingHorizontal = dp2px(density, DEFAULT_PADDING_HORIZONTAL);
        TextView textView = new TextView(context);
        textView.setTextSize(DEFAULT_TEXT_SIZE);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, heightPx);
        textView.setLayoutParams(params);
        custom(textView, parent);
        return textView;
    }

    @Override
    protected void onBind(@NonNull LwViewHolder holder, @NonNull Object item) {
        TextView textView = holder.getView();
        textView.setText(item.toString());
    }

    private int dp2px(float density, float dp) {
        return (int) (density * dp + 0.5f);
    }

    protected void custom(TextView textView, ViewGroup parent) {

    }
}
