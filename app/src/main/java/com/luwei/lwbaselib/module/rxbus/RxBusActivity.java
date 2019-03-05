package com.luwei.lwbaselib.module.rxbus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;
import com.luwei.rxbus.BaseEvent;
import com.luwei.rxbus.RxBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * rxbus 的使用示例
 */
public class RxBusActivity extends LwBaseActivity {


    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_change_text)
    Button btnChangeText;


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void initEvent() {
        RxBus.getInstance()
                .register(this)
                .ofType(BaseEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseEvent -> tvContent.setText(baseEvent.getContent()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_bus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
    }

    @Override
    public IPresent newP() {
        return null;
    }


    @OnClick({R.id.btn_change_text, R.id.btn_send_stick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_text:
                new Thread(() -> {
                    RxBus.getInstance()
                            .post(new BaseEvent(0, etInput.getText().toString()));

                }).start();
                break;
            case R.id.btn_send_stick:
                RxBus.getInstance().postStick(new StickEvent(0,etInput.getText().toString()));
                startActivity(new Intent(this,StickReceiverActivity.class));
                break;
        }
    }
}
