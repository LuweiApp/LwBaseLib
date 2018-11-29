package com.luwei.lwbaselib.module.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;
import com.luwei.recyclerview.adapter.extension.SampleBinder;
import com.luwei.recyclerview.adapter.multitype.Items;
import com.luwei.recyclerview.adapter.multitype.LwAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/15
 */
public class AdapterDemoActivity extends LwBaseActivity {
    @BindView(R.id.rv_adapter_demo)
    RecyclerView rvAdapterDemo;

    LwAdapter mAdapter;
    private Items mItems;

    @Override
    public void initView(Bundle savedInstanceState) {
        rvAdapterDemo.setLayoutManager(new LinearLayoutManager(this));
    }

    public void reset() {
        mItems.clear();
        mAdapter = new LwAdapter(mItems);
        rvAdapterDemo.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        mItems = new Items();
        toSingleType();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_adapter;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.btn_adapter_single, R.id.btn_adapter_multi, R.id.btn_adapter_header, R.id.btn_adapter_footer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_adapter_single: //单一类型
                toSingleType();
                break;
            case R.id.btn_adapter_multi:  //多类型
                toMultiType();
                break;
            case R.id.btn_adapter_header:
                addHeader();
                break;
            case R.id.btn_adapter_footer:
                addFooter();
                break;
        }
    }

    /**
     * 单类型
     */
    private void toSingleType() {
        reset();
        mAdapter.register(RichTextBean.class, new RichTextBinder());
        for (int i = 0; i < 20; i++) {
            mItems.add(new RichTextBean(R.mipmap.ic_launcher_round, "第" + i + "条数据"));
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 多类型
     */
    private void toMultiType() {
        reset();
        //多少个类型就注册多少个
        mAdapter.register(RichTextBean.class, new RichTextBinder());
        mAdapter.register(String.class, new SampleBinder());
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                mItems.add(new RichTextBean(R.mipmap.ic_launcher_round, "第" + i  + "个富文本"));
            } else {
                mItems.add("第" + i + "个纯文本");
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 增加 Header
     */
    private void addHeader() {
        int existedSize = mAdapter.getHeaderSize();
        RichTextBean bean = new RichTextBean(R.mipmap.ic_launcher_round, "第" + existedSize  + "个Header");
        mAdapter.addHeader(bean);
    }

    /**
     * 增加 Footer
     */
    public void addFooter() {
        int existedSize = mAdapter.getFooterSize();
        RichTextBean bean = new RichTextBean(R.mipmap.ic_launcher_round, "第" + existedSize + "个Footer");
        mAdapter.addFooter(bean);
    }
}
