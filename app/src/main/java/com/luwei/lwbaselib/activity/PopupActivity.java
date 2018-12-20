package com.luwei.lwbaselib.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.module.popup.ListPopup;
import com.luwei.lwbaselib.module.popup.ConfirmPopup;
import com.luwei.ui.popup.CustomPopup;
import com.luwei.ui.popup.XGravity;
import com.luwei.ui.popup.YGravity;
import com.luwei.ui.view.TitleBar;

import butterknife.OnClick;

public class PopupActivity extends LwBaseActivity {

    private CustomPopup mCustomPopup;
    private ConfirmPopup mConfirmPopup;
    private ListPopup mListPopup;



    @OnClick({R.id.btn_up, R.id.btn_left, R.id.btn_right, R.id.btn_bottom, R.id.btn_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                mCustomPopup.showAtAnchorView(view,YGravity.BELOW,XGravity.ALIGN_LEFT);
                break;
            case R.id.btn_left:
                mConfirmPopup.showAtAnchorView(view,YGravity.CENTER,XGravity.RIGHT);

                break;
            case R.id.btn_right:
                mCustomPopup.showAtAnchorView(view,YGravity.CENTER,XGravity.LEFT);

                break;
            case R.id.btn_bottom:
                mListPopup.showAtAnchorView(view, YGravity.ABOVE, XGravity.CENTER);
                break;
            case R.id.btn_center:
                mCustomPopup.showAtLocation(getWindow().getDecorView(),Gravity.CENTER,0,0);
                break;
        }
    }


    /**
     * 创建自定义Popup
     */
    private void initCustomPopup(){
         mCustomPopup = CustomPopup.newInstance(this)
                .setContentView(R.layout.popup_custom)
                .setFocusAndOutsideEnable(true)
                .setOnViewListener(new CustomPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, CustomPopup popup) {
                        TextView tvOk = view.findViewById(R.id.tv_popup_yes);
                        TextView tvCancel = view.findViewById(R.id.tv_popup_no);
                        tvOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),"yes",
                                        Toast.LENGTH_SHORT).show();
                                popup.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popup.dismiss();
                            }
                        });
                    }
                })
                .create();
    }

    /**
     * 封装的确认取消Popup
     */
    private void initConfirmPopup(){
        mConfirmPopup = ConfirmPopup.newInstance()
                .setContext(this)
                .setOnOkClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"yes",
                                Toast.LENGTH_SHORT).show();
                        mConfirmPopup.dismiss();
                    }
                })
                .setOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mConfirmPopup.dismiss();
                    }
                })
                .create();
    }

    /**
     * 封装的列表Popup 添加背景阴影
     */
    private void initListPopup() {
        mListPopup = ListPopup.newInstance(this)
                .setBackgroundDimEnable(true)
                .create();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initCustomPopup();
        initConfirmPopup();
        initListPopup();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_popup;
    }

    @Override
    public IPresent newP() {
        return null;
    }
}
