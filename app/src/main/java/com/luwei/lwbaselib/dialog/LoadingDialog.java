package com.luwei.lwbaselib.dialog;

import android.os.Bundle;
import android.text.TextUtils;

import com.luwei.lwbaselib.R;

import cn.luwei.mvp.ui.dialog.BaseDialog;
import cn.luwei.mvp.ui.dialog.DialogViewHolder;

/**
 * Created by runla on 2018/10/31.
 * 文件描述：
 */

public class LoadingDialog extends BaseDialog {
    private String loadingText;
    public static LoadingDialog newInstance(){
        return new LoadingDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置屏幕占比
        setDialogWidthPercent(0.5f);
        //
        setTransparent(true);
        // 设置动画效果
        setAnimationsStyleId(R.style.ProgressDialogAnimation);

        if (savedInstanceState != null) {
            loadingText = savedInstanceState.getString("loadingText");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("loadingText",loadingText);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void onCreateView(DialogViewHolder viewHolder, BaseDialog dialog) {
        if (!TextUtils.isEmpty(loadingText)) {
            viewHolder.setText(R.id.tv_loading,loadingText);
        }
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }
}
