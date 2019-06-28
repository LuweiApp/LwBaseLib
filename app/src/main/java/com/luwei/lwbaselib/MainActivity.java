package com.luwei.lwbaselib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.activity.DialogActivity;
import com.luwei.lwbaselib.activity.HeaderActivity;
import com.luwei.lwbaselib.activity.ImageActivity;
import com.luwei.lwbaselib.activity.ImagePreviewActivity;
import com.luwei.lwbaselib.activity.LogActivity;
import com.luwei.lwbaselib.activity.PermissionActivity;
import com.luwei.lwbaselib.activity.PopupActivity;
import com.luwei.lwbaselib.activity.SimpleForResultActivity;
import com.luwei.lwbaselib.module.rxbus.RxBusActivity;
import com.luwei.lwbaselib.activity.TimerButtonActivity;
import com.luwei.lwbaselib.activity.TitleBarAcitivity;
import com.luwei.lwbaselib.activity.UMShareAndAuthActivity;
import com.luwei.lwbaselib.module.banner.BannerActivity;
import com.luwei.lwbaselib.module.recyclerview.RecyclerViewActivity;
import com.luwei.util.forresult.SimpleForResult;

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
            , R.id.btn_to_recyclerview, R.id.btn_to_permission, R.id.btn_to_RxBus,
            R.id.btn_to_toast, R.id.btn_activity_for_result, R.id.btn_preview, R.id.btn_header,
            R.id.btn_banner, R.id.btn_timer_button, R.id.btn_titlebar, R.id.btn_share})
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
            case R.id.btn_to_recyclerview:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.btn_to_permission:
                startActivity(new Intent(MainActivity.this, PermissionActivity.class));
                break;
            case R.id.btn_to_RxBus:
                startActivity(new Intent(MainActivity.this, RxBusActivity.class));
                break;
            case R.id.btn_to_toast:
                startActivity(new Intent(MainActivity.this, SimpleForResultActivity.class));
                break;
            case R.id.btn_activity_for_result:
                // 简化调用 startActivityForResult 及避免在 onActivityResult 中处理繁琐的结果
                SimpleForResult simpleForResult = new SimpleForResult(this);
                simpleForResult.startForResult(SimpleForResultActivity.class)
                        .subscribe((resultInfo) -> {
                            if (resultInfo.getData() != null) {
                                ToastUtils.showLong(resultInfo.getData().getStringExtra("result"));
                            }
                        });
                break;
            case R.id.btn_preview:
                startActivity(new Intent(MainActivity.this, ImagePreviewActivity.class));
                break;
            case R.id.btn_header:
                startActivity(new Intent(MainActivity.this, HeaderActivity.class));
                break;
            case R.id.btn_banner:
                //banner
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
                break;
            case R.id.btn_timer_button:
                //倒计时控件
                startActivity(new Intent(MainActivity.this, TimerButtonActivity.class));
                break;
            case R.id.btn_titlebar:
                //标题栏
                startActivity(new Intent(MainActivity.this, TitleBarAcitivity.class));
                break;
            case R.id.btn_share:
                //友盟分享和授权  因为base库并未在各开发平台注册，所以无法使用分享和授权功能，仅做代码演示用
                startActivity(new Intent(MainActivity.this, UMShareAndAuthActivity.class));
                break;
            default:
                break;
        }
    }
}
