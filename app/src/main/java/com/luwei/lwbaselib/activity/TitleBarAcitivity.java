package com.luwei.lwbaselib.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.luwei.lwbaselib.R;
import com.luwei.ui.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author licheng
 */
public class TitleBarAcitivity extends AppCompatActivity {

    @BindView(R.id.tb_1)
    TitleBar mTb1;
    @BindView(R.id.tb_2)
    TitleBar mTb2;
    @BindView(R.id.tb_3)
    TitleBar mTb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_bar_acitivity);
        ButterKnife.bind(this);

        mTb2.setLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void leftClick() {
                ToastUtils.showShort("点击左部");
            }
        });
        mTb2.setLeftText("测试");
        mTb2.setTitleText("点击左部");
        mTb2.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }
}
