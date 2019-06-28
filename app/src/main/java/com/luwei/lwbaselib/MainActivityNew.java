package com.luwei.lwbaselib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.activity.DialogActivity;
import com.luwei.lwbaselib.activity.HeaderActivity;
import com.luwei.lwbaselib.activity.ImageActivity;
import com.luwei.lwbaselib.activity.ImagePreviewActivity;
import com.luwei.lwbaselib.activity.PermissionActivity;
import com.luwei.lwbaselib.activity.PopupActivity;
import com.luwei.lwbaselib.activity.SimpleForResultActivity;
import com.luwei.lwbaselib.activity.TimerButtonActivity;
import com.luwei.lwbaselib.activity.TitleBarAcitivity;
import com.luwei.lwbaselib.activity.UMShareAndAuthActivity;
import com.luwei.lwbaselib.adapter.MainNaviBinder;
import com.luwei.lwbaselib.bean.MainNaviBean;
import com.luwei.lwbaselib.module.banner.BannerActivity;
import com.luwei.lwbaselib.module.recyclerview.RecyclerViewActivity;
import com.luwei.lwbaselib.module.rxbus.RxBusActivity;
import com.luwei.recyclerview.adapter.multitype.Items;
import com.luwei.recyclerview.adapter.multitype.LwAdapter;

/**
 * @author LiCheng
 * @date 2019-06-27
 */
public class MainActivityNew extends LwBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(actionBar.getTitle() + " v1.2.3");


        //RecycleView通过binder模式添加数据的标准流程
        RecyclerView rvMain = findViewById(R.id.rv_main);
        //1.创建Items，存储特定bean类型的数据
        Items items = new Items();
        //2.创建LwAdapter对象，添加之前的items
        LwAdapter adapter = new LwAdapter(items);
        //3.为adapter注册，传入bean类型Class和binder，所有操作都在binder中处理
        adapter.register(MainNaviBean.class, new MainNaviBinder());
        //4.为RecycleView添加上面的adapter
        rvMain.setAdapter(adapter);
        //5.别忘了给RecycleView添加布局管理，否则不会显示
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        //给items添加数据，可以单个add，也可以addAll添加数组
        items.clear();
        items.add(new MainNaviBean("图片加载ImageLoadUtils", ImageActivity.class));
        items.add(new MainNaviBean("弹窗Dialog", DialogActivity.class));
        items.add(new MainNaviBean("弹窗PopupWindow", PopupActivity.class));
        items.add(new MainNaviBean("RecyclerView相关", RecyclerViewActivity.class));
        items.add(new MainNaviBean("权限获取Permission", PermissionActivity.class));
        items.add(new MainNaviBean("事件订阅及接收Rxbus", RxBusActivity.class));
        items.add(new MainNaviBean("简单返回事件传递SimpleForResult", SimpleForResultActivity.class));
        items.add(new MainNaviBean("通用标题栏TitleBar", TitleBarAcitivity.class));
        items.add(new MainNaviBean("倒计时按钮控件TimerButton", TimerButtonActivity.class));
        items.add(new MainNaviBean("图片预览ImagePreview", ImagePreviewActivity.class));
        items.add(new MainNaviBean("图片选择ImageSelector", HeaderActivity.class));
        items.add(new MainNaviBean("横幅广告Banner", BannerActivity.class));
        items.add(new MainNaviBean("友盟分享集成", UMShareAndAuthActivity.class));
        //刷新adapter，使更新后的items数据生效
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_new;
    }

    @Override
    public IPresent newP() {
        return null;
    }
}
