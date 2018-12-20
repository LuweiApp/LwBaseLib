package com.luwei.lwbaselib.module.popup;

import android.view.View;
import android.widget.TextView;

import com.luwei.lwbaselib.R;
import com.luwei.ui.popup.BasePopup;

/**
 * Created by LiCheng
 * Date：2018/12/8
 */
public class ConfirmPopup extends BasePopup<ConfirmPopup> {

    private View.OnClickListener mCancelListener;
    private View.OnClickListener mOkListener;

    public static ConfirmPopup newInstance(){
        return new ConfirmPopup();
    }


    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_custom);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view, ConfirmPopup popup) {
        TextView tvOk = view.findViewById(R.id.tv_popup_yes);
        TextView tvCancel = view.findViewById(R.id.tv_popup_no);
        tvOk.setOnClickListener(mOkListener);
        tvCancel.setOnClickListener(mCancelListener);
    }

    public ConfirmPopup setOnCancelClickListener(View.OnClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public ConfirmPopup setOnOkClickListener(View.OnClickListener listener) {
        mOkListener = listener;
        return this;
    }


}
