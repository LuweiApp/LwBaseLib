package com.luwei.lwbaselib.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.adapter.PopupListAdapter;
import com.luwei.ui.popup.CustomPopupWindow;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupActivity extends AppCompatActivity {

    private View mRootView;
    private CustomPopupWindow.Builder builder;
    private CustomPopupWindow customPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        ButterKnife.bind(this);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_popup,null,false);
        View mContentView = LayoutInflater.from(this).inflate(R.layout.popup_list,null,false);
        initList(mContentView);
        builder = new CustomPopupWindow.Builder(this);
        customPopupWindow = builder.setView(mContentView).create();

    }

    private void initList(View contentView){
        RecyclerView recyclerView = contentView.findViewById(R.id.rlv_popup_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        PopupListAdapter adapter = new PopupListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:

                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:

                break;
            case R.id.btn_4:
                new CustomPopupWindow.Builder(this).setView(R.layout.popup_custom).create().showAsDropDown(view);
                break;
            case R.id.btn_5:
                customPopupWindow.showAsDropDown(view);
                break;
            case R.id.btn_6:

                break;
            case R.id.btn_7:

                break;
            case R.id.btn_8:

                break;
            case R.id.btn_9:
                break;
        }
    }
}
