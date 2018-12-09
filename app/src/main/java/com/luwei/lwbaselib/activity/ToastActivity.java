package com.luwei.lwbaselib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ActionMenuView;
import android.widget.Button;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Toast 的使用示例
 */
public class ToastActivity extends LwBaseActivity {

    @BindView(R.id.btn_finish)
    Button btnFinish;

    @OnClick(R.id.btn_finish)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("result","测试返回值 success");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toast;
    }

    @Override
    public IPresent newP() {
        return null;
    }
}
