package com.luwei.lwbaselib.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.module.popup.ListPopup;
import com.luwei.lwbaselib.module.popup.ConfirmPopup;
import com.luwei.ui.popup.CustomPopup;
import com.luwei.ui.popup.XGravity;
import com.luwei.ui.popup.YGravity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupActivity extends AppCompatActivity {

    private CustomPopup mCustomPopup;
    private ConfirmPopup mConfirmPopup;
    private ListPopup mListPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        ButterKnife.bind(this);
        initCustomPopup();
        initConfirmPopup();
        initListPopup();
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


    @OnClick({R.id.btn_up, R.id.btn_left, R.id.btn_right, R.id.btn_bottom, R.id.btn_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                mListPopup.showAtAnchorView(view,YGravity.BELOW,XGravity.CENTER);
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
                View rootView = View.inflate(this,R.layout.popup_custom,null);
                mConfirmPopup.showAtLocation(rootView,Gravity.CENTER,0,0);
                break;
        }
    }


}
