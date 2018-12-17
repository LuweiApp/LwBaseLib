package com.luwei.lwbaselib.module.recyclerview.decoration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.luwei.lwbaselib.R;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/6
 */
public class DecorationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);
        findViewById(R.id.btn_to_grid_space).setOnClickListener(this);
        findViewById(R.id.btn_to_table).setOnClickListener(this);
        findViewById(R.id.btn_to_staggered).setOnClickListener(this);
        findViewById(R.id.btn_to_stick).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_grid_space:
                //网格间隔
                startActivity(new Intent(this,GridSpaceActivity.class));
                break;
            case R.id.btn_to_table:
                //表格
                startActivity(new Intent(this, TableDividerActivity.class));
                break;
            case R.id.btn_to_staggered:
                //瀑布流
                startActivity(new Intent(this,StaggeredActivity.class));
                break;
            case R.id.btn_to_stick:
                //粘性头部
                startActivity(new Intent(this,StickHeaderActivity.class));
                break;
        }
    }
}
