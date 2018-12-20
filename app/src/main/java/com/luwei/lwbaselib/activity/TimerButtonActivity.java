package com.luwei.lwbaselib.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;
import com.luwei.ui.view.TimerButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/18
 */
public class TimerButtonActivity extends LwBaseActivity {


    @BindView(R.id.view_timer_btn)
    TimerButton mViewTimerBtn;

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewTimerBtn.setCallback(new TimerButton.Callback() {
            @Override
            public void onFinish(TimerButton button) {
                ToastUtils.showShort("完成");
            }

            @Override
            public void onTick(TimerButton button, long millisUntilFinished) {
                if ((millisUntilFinished + 500) / 1000 == 3) {
                    button.setStartedColor(getResources().getColor(R.color.black_000));
                }
                ToastUtils.showShort((millisUntilFinished + 500) / 1000 + "");
            }
        });
        mViewTimerBtn.setFinishedText("重新开始");
        mViewTimerBtn.setFormatText("还剩%d秒");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_timer_button;
    }

    @Override
    public IPresent newP() {
        return null;
    }

    @OnClick(R.id.view_timer_btn)
    public void onViewClicked() {
        mViewTimerBtn.start();
    }
}
