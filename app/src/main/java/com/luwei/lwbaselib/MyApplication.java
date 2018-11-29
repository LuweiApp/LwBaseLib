package com.luwei.lwbaselib;

import android.app.Application;

import cn.luwei.mvp.imageloader.ImageLoaderUtils;

/**
 * Created by runla on 2018/10/29.
 * 文件描述：
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtils.init();
    }
}
