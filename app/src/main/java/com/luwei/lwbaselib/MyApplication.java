package com.luwei.lwbaselib;

import android.app.Application;
import android.graphics.Color;

import com.luwei.ui.TitleBar;
import com.luwei.util.imageloader.ImageLoaderUtils;


/**
 * Created by runla on 2018/10/29.
 * 文件描述：
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtils.init();
/*        TitleBar.getConfig()
                .setTitleTextColor(Color.RED)
                .setPadding(this,25)
                .setTitleTextSize(this,20);*/
    }
}
