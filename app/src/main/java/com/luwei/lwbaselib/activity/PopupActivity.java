package com.luwei.lwbaselib.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.adapter.PopupListAdapter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.luwei.mvp.popupwindow.CustomPopupWindow;

public class PopupActivity extends AppCompatActivity {

    private CustomPopupWindow mPopupWindow;
    private int x;
    private int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        ButterKnife.bind(this);
    }

    private void initNormalPopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_custom, null);
        mPopupWindow = new CustomPopupWindow.Builder(this)
                .setView(contentView)
                .create();

        TextView tvPopupYes = contentView.findViewById(R.id.tv_popup_yes);
        TextView tvPopupNo = contentView.findViewById(R.id.tv_popup_no);
        tvPopupYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "确认", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
            }
        });
        tvPopupNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
            }
        });
    }

    private void initListPopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) return;
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_list, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.rlv_popup_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        PopupListAdapter adapter = new PopupListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mPopupWindow = new CustomPopupWindow.Builder(this)
                .setView(contentView)
                .create();
    }


    @OnClick({R.id.btn_up, R.id.btn_left, R.id.btn_right, R.id.btn_bottom, R.id.btn_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                initListPopup();
                x = (view.getWidth() - mPopupWindow.getWidth()) / 2;
                mPopupWindow.showAsDropDown(view, x, 0);
                break;
            case R.id.btn_left:
                initNormalPopup();
                x = view.getWidth();
                y = -(view.getHeight() + mPopupWindow.getHeight()) / 2;
                mPopupWindow.showAsDropDown(view, x, y);
                break;
            case R.id.btn_right:
                initNormalPopup();
                x = -mPopupWindow.getWidth();
                y = -(view.getHeight() + mPopupWindow.getHeight()) / 2;
                mPopupWindow.showAsDropDown(view, x, y);
                break;
            case R.id.btn_bottom:
                break;
            case R.id.btn_center:
                initNormalPopup();
                mPopupWindow.showAtLocationById(R.layout.popup_custom, Gravity.CENTER, 0, 0);
                break;
        }
    }


}
