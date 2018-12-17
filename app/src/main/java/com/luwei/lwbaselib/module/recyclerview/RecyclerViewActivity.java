package com.luwei.lwbaselib.module.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.module.recyclerview.adapter.AdapterDemoActivity;
import com.luwei.lwbaselib.module.recyclerview.decoration.DecorationActivity;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/6
 */
public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        findViewById(R.id.btn_to_adapter).setOnClickListener(this);
        findViewById(R.id.btn_to_decoration).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_to_adapter:
                startActivity(new Intent(RecyclerViewActivity.this, AdapterDemoActivity.class));
                break;
            case R.id.btn_to_decoration:
                startActivity(new Intent(RecyclerViewActivity.this, DecorationActivity.class));
                break;
        }
    }
}
