package com.luwei.lwbaselib.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.ImageUrlConfig;
import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.adapter.ImageBinder;
import com.luwei.lwbaselib.bean.ImageViewInfo;
import com.luwei.recyclerview.adapter.extension.Extension;
import com.luwei.recyclerview.adapter.extension.LwItemBinder;
import com.luwei.recyclerview.adapter.extension.LwViewHolder;
import com.luwei.recyclerview.adapter.multitype.Items;
import com.luwei.recyclerview.adapter.multitype.LwAdapter;
import com.luwei.recyclerview.decoration.GridSpaceDecoration;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePreviewActivity extends LwBaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Items mItems;
    private LwAdapter mAdapter;
    private ImageBinder mImageBinder;
    private ArrayList<ImageViewInfo> mThumbViewInfoList = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;

    @Override
    public void initView(Bundle savedInstanceState) {
        mGridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.addItemDecoration(new GridSpaceDecoration(20,20));

        mItems = new Items();
        mAdapter = new LwAdapter(mItems);
        mImageBinder = new ImageBinder(this);
        mAdapter.register(ImageViewInfo.class,mImageBinder);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {


        for (String s : ImageUrlConfig.getUrls()) {
            mThumbViewInfoList.add(new ImageViewInfo(s));
        }
        mItems.addAll(mThumbViewInfoList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void initEvent() {
        mImageBinder.setOnItemClickListener((holder, item) -> {
            // 调用预览之前，需要设置图片的加载器，ZoomMediaLoader.getInstance().init(new ImageLoader());
            computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
            GPreviewBuilder.from(ImagePreviewActivity.this)
                    .setData(mThumbViewInfoList)
                    .setCurrentIndex(mItems.indexOf(item))
                    .setSingleFling(true)
                    .setType(GPreviewBuilder.IndicatorType.Number)
                    .start();

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_preview;
    }

    @Override
    public IPresent newP() {
        return null;
    }

    /**
     * 主要是为了设置图片的位置信息
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mItems.size(); i++) {
            View itemView = recyclerView.getChildAt(i - firstCompletelyVisiblePos);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.image);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }
}
