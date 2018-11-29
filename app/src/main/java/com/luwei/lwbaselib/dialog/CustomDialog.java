package com.luwei.lwbaselib.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.luwei.lwbaselib.R;

import cn.luwei.mvp.ui.dialog.BaseDialog;
import cn.luwei.mvp.ui.dialog.DialogViewHolder;


/**
 * Created by runla on 2018/10/31.
 * 文件描述：
 */

public class CustomDialog extends BaseDialog {
    private String title;
    private String content;
    private String cancel;
    private String confirm;
    private int titleColor;
    private int contentColor;
    private int cancelColor;
    private int confirmColor;

    public static CustomDialog newInstance() {
        return new CustomDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            titleColor = savedInstanceState.getInt("titleColor");
            contentColor = savedInstanceState.getInt("contentColor");
            cancelColor = savedInstanceState.getInt("cancelColor");
            confirmColor = savedInstanceState.getInt("confirmColor");
            title =savedInstanceState.getString("title",null);
            content =savedInstanceState.getString("content",null);
            cancel =savedInstanceState.getString("cancel",null);
            confirm =savedInstanceState.getString("confirm",null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("titleColor",titleColor);
        outState.putInt("contentColor",contentColor);
        outState.putInt("cancelColor",cancelColor);
        outState.putInt("confirmColor",confirmColor);
        outState.putString("title",title);
        outState.putString("content",content);
        outState.putString("cancel",cancel);
        outState.putString("confirm",confirm);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_custom;
    }

    @Override
    public void onCreateView(DialogViewHolder viewHolder, BaseDialog dialog) {
        if (!TextUtils.isEmpty(title)) {
            viewHolder.setVisibility(R.id.tv_title, true);
        }
        if (!TextUtils.isEmpty(cancel)) {
            viewHolder.setText(R.id.tv_cancel, cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {
            viewHolder.setText(R.id.tv_confirm, confirm);
        }
        viewHolder.setText(R.id.tv_content, content);

        if (titleColor != 0) {
            viewHolder.setTextColor(R.id.tv_title, titleColor);
        }
        if (contentColor != 0) {
            viewHolder.setTextColor(R.id.tv_content, contentColor);
        }
        if (cancelColor != 0) {
            viewHolder.setTextColor(R.id.tv_cancel, cancelColor);
        }
        if (confirmColor != 0) {
            viewHolder.setTextColor(R.id.tv_confirm, confirmColor);
        }

        viewHolder.getView(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmLitener != null) {
                    mConfirmLitener.onClickConfirm();
                }
                CustomDialog.this.dismiss();
            }
        });

        viewHolder.getView(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.onClickCancel();
                }
                CustomDialog.this.dismiss();
            }
        });


    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public CustomDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    public CustomDialog setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    public CustomDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public CustomDialog setContentColor(int contentColor) {
        this.contentColor = contentColor;
        return this;
    }

    public CustomDialog setCancelColor(int cancelColor) {
        this.cancelColor = cancelColor;
        return this;
    }

    public CustomDialog setConfirmColor(int confirmColor) {
        this.confirmColor = confirmColor;
        return this;
    }
}
