package com.luwei.lwbaselib.dialog;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.luwei.lwbaselib.R;

import cn.luwei.mvp.ui.dialog.BaseDialog;
import cn.luwei.mvp.ui.dialog.DialogViewHolder;

/**
 * Created by runla on 2018/10/31.
 * 文件描述：
 */

public class ProgressDialog extends BaseDialog{

    public static ProgressDialog newInstance(){
        return new ProgressDialog();
    }
    private int progress=0;
    private int maxProgress = 100;
    private ProgressBar progressBar;
    private TextView progressTip;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_progress;
    }

    @Override
    public void onCreateView(DialogViewHolder viewHolder, BaseDialog dialog) {
        progressBar = viewHolder.getView(R.id.progressBar);
        progressTip = viewHolder.getView(R.id.tv_progress);
        progressBar.setMax(maxProgress);
        progressBar.setProgress(progress);
        progressTip.setText(progress+"/"+maxProgress);
    }

    public ProgressDialog setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }

    public ProgressDialog setProgress(int progress){
        this.progress = progress;
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressTip.setText(progress+"/"+maxProgress);
        }
        return this;
    }
}
