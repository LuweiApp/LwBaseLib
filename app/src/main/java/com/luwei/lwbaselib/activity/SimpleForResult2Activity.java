package com.luwei.lwbaselib.activity;

import android.os.Bundle;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LiCheng
 * @date 2019-06-28
 */
public class SimpleForResult2Activity extends LwBaseActivity {
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
        return R.layout.activity_simple_for_result_2;
    }

    @Override
    public IPresent newP() {
        return null;
    }


    @OnClick(R.id.btn_change_text)
    public void onViewClicked() {
        setResult(RESULT_OK);
        finish();
    }
}
