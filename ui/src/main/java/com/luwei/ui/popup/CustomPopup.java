package com.luwei.ui.popup;

import android.content.Context;
import android.view.View;

/**
 * Created by LiCheng
 * Date：2018/12/10
 */
public class CustomPopup extends BasePopup<CustomPopup> {

    private OnViewListener mOnViewListener;


    private CustomPopup() {
    }

    private CustomPopup(Context context) {
        setContext(context);
    }

    public static CustomPopup newInstance(){
        return new CustomPopup();
    }

    public static CustomPopup newInstance(Context context){
        return new CustomPopup(context);
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void initViews(View view, CustomPopup popup) {
        if (mOnViewListener != null) {
            mOnViewListener.initViews(view, popup);
        }
    }

    public CustomPopup setOnViewListener(OnViewListener listener) {
        this.mOnViewListener = listener;
        return this;
    }

    public interface OnViewListener {

        void initViews(View view, CustomPopup popup);
    }
}
