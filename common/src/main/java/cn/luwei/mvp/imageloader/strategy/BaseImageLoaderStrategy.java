package cn.luwei.mvp.imageloader.strategy;

import android.content.Context;
import android.widget.ImageView;


import cn.luwei.mvp.imageloader.ImageLoaderConfig;

/**
 * Created by runla on 2018/10/29.
 * 文件描述：加载图片框架的策略接口
 */

public interface BaseImageLoaderStrategy {
    /**
     * 设置图片加载器的配置
     * @param config
     */
    void setLoaderConfig(ImageLoaderConfig config);

    /**
     * 默认加载图片的形式
     * @param context
     * @param imageView
     * @param imgUrl
     */
    void loadImage(Context context, ImageView imageView, Object imgUrl);

    /**
     * 从 drawable 中加载 image
     * @param context
     * @param imageView
     * @param resId
     */
    void loadImageFromDrawable(Context context,ImageView imageView,int resId);

    /**
     * 从手机本地加载图片
     * @param context
     * @param imageView
     * @param path
     */
    void loadImageFromLocal(Context context,ImageView imageView,String path);

    /**
     * 加载 gif 图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    void loadGifImage(Context context,ImageView imageView,Object imgUrl);

    /**
     * 加载圆形图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    void loadCircleImage(Context context, ImageView imageView, Object imgUrl);

    /**
     *  加载圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     * @param radius
     */
    void loadRoundedImage(Context context, ImageView imageView, Object imgUrl, int radius);

    /**
     * 加载高斯模糊图片
     * @param context
     * @param imageView
     * @param imgUrl
     * @param blurRadius
     */
    void loadBlurImage(Context context,ImageView imageView,Object imgUrl,int blurRadius);

    /**
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    void loadMarkImage(Context context,ImageView imageView,Object imgUrl,int maskId);

    /**
     * 清理内存
     * @param context
     */
    void clearMemory(Context context);
}
