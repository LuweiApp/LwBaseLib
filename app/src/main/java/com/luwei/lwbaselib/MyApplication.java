package com.luwei.lwbaselib;

import android.app.Application;
import android.graphics.Color;

import com.luwei.ui.view.TitleBar;
import com.luwei.util.imageloader.ImageLoaderUtils;
import com.previewlibrary.ZoomMediaLoader;


/**
 * Created by runla on 2018/10/29.
 * 文件描述：
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtils.init();

//        TitleBar.getConfig()
//                .setTitleTextColor(Color.RED)
//                .setTitleTextSize(this,20)
//                .setBackGroundColor(Color.GRAY);

        ZoomMediaLoader.getInstance().init(new PreviewPictureLoader());

    }
}
