package com.luwei.lwbaselib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.activity.DialogActivity;
import com.luwei.lwbaselib.activity.ImageActivity;
import com.luwei.lwbaselib.activity.LogActivity;
import com.luwei.lwbaselib.activity.PermissionActivity;
import com.luwei.lwbaselib.activity.PopupActivity;
import com.luwei.lwbaselib.activity.RxBusActivity;
import com.luwei.lwbaselib.activity.ToastActivity;
import com.luwei.lwbaselib.module.recyclerview.AdapterDemoActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends LwBaseActivity {

    @BindView(R.id.tv_content)
    TextView mTvContent;


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        mTvContent.setText("hello world!!!");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public IPresent newP() {
        return null;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.btn_to_image, R.id.btn_to_log, R.id.btn_to_dialog, R.id.btn_to_popup
            , R.id.btn_to_adapter,R.id.btn_to_permission, R.id.btn_to_RxBus, R.id.btn_to_toast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_to_image:
                startActivity(new Intent(MainActivity.this, ImageActivity.class));
                break;
            case R.id.btn_to_log:
                startActivity(new Intent(MainActivity.this, LogActivity.class));
                break;
            case R.id.btn_to_dialog:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            case R.id.btn_to_popup:
                startActivity(new Intent(MainActivity.this, PopupActivity.class));
                break;
            case R.id.btn_to_adapter:
                startActivity(new Intent(MainActivity.this, AdapterDemoActivity.class));
                break;
            case R.id.btn_to_permission:
                startActivity(new Intent(MainActivity.this, PermissionActivity.class));
                break;
            case R.id.btn_to_RxBus:
                startActivity(new Intent(MainActivity.this, RxBusActivity.class));
                break;
            case R.id.btn_to_toast:
                startActivity(new Intent(MainActivity.this, ToastActivity.class));
                break;
            default:
                break;
        }
    }
}
