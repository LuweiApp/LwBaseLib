package com.luwei.lwbaselib;

import android.app.Application;

import com.previewlibrary.ZoomMediaLoader;


/**
 * Created by runla on 2018/10/29.
 * 文件描述：
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ImageLoaderUtils.getInstance().init();

//        TitleBar.getConfig()
//                .setTitleTextColor(Color.RED)
//                .setTitleTextSize(this,20)
//                .setBackGroundColor(Color.GRAY);


//        UShareUtils.setConfig(new UShareConfig.Builder()
//                .setTitle("")
//                .setCopyUrlBtnIcon("banner_4")
//                .create());

        ZoomMediaLoader.getInstance().init(new PreviewPictureLoader());

    }
}
