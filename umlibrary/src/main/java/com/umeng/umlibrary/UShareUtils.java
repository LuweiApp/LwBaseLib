package com.umeng.umlibrary;

import android.graphics.Bitmap;

import com.umeng.umlibrary.media.UMediaGif;
import com.umeng.umlibrary.media.UMediaImage;
import com.umeng.umlibrary.media.UMediaMinAPP;
import com.umeng.umlibrary.media.UMediaMusic;
import com.umeng.umlibrary.media.UMediaText;
import com.umeng.umlibrary.media.UMediaVideo;
import com.umeng.umlibrary.media.UMediaWeb;

import java.io.File;

/**
 * @author LiCheng
 * @date 2019/3/1
 */
public class UShareUtils {

    /**
     * 全局设置分享平台和监听器
     */
    private static UShareConfig mConfig = new UShareConfig.Builder().create();

    /**
     * 获取设置
     */
    public static UShareConfig getConfig() {
        return mConfig;
    }

    public static void setConfig(UShareConfig config){
        mConfig = config;
    }

    /**
     * 纯文本分享
     *
     * @param text
     * @return
     */
    public static UMediaText createText(String text) {
        return new UMediaText(text);
    }

    /**
     * 网络图片分享
     *
     * @param imgUrl
     * @return
     */
    public static UMediaImage createImage(String imgUrl) {
        return new UMediaImage(imgUrl);
    }

    /**
     * 资源文件分享
     *
     * @param resource
     * @return
     */
    public static UMediaImage createImage(int resource) {
        return new UMediaImage(resource);
    }

    /**
     * Bitmap图片分享
     *
     * @param bitmap
     * @return
     */
    public static UMediaImage createImage(Bitmap bitmap) {
        return new UMediaImage(bitmap);
    }

    /**
     * 文件图片分享
     *
     * @param file
     * @return
     */
    public static UMediaImage createImage(File file) {
        return new UMediaImage(file);
    }

    /**
     * 流图片分享
     *
     * @param bytes
     * @return
     */
    public static UMediaImage createImage(byte[] bytes) {
        return new UMediaImage(bytes);
    }

    /**
     * 分享链接
     *
     * @param webUrl
     * @return
     */
    public static UMediaWeb createWeb(String webUrl) {
        return new UMediaWeb(webUrl);
    }

    /**
     * 分享网络视频
     *
     * @param videoUrl
     * @return
     */
    public static UMediaVideo createVideo(String videoUrl) {
        return new UMediaVideo(videoUrl);
    }

    /**
     * 分享网络音乐
     *
     * @param musicUrl
     * @return
     */
    public static UMediaMusic createMusic(String musicUrl) {
        return new UMediaMusic(musicUrl);
    }

    /**
     * 分享动图表情
     * 仅支持分享到微信好友
     *
     * @param gifUrl
     * @return
     */
    public static UMediaGif createGif(String gifUrl) {
        return new UMediaGif(gifUrl);
    }

    /**
     * 分享小程序
     * 仅支持分享到微信好友
     * 小程序需要和应用在同一个微信开发平台上
     *
     * @param minAPPUrl
     * @return
     */
    public static UMediaMinAPP createMinApp(String minAPPUrl) {
        return new UMediaMinAPP(minAPPUrl);
    }

}

