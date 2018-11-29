package cn.luwei.mvp.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.luwei.mvp.R;
import cn.luwei.mvp.imageloader.strategy.BaseImageLoaderStrategy;
import cn.luwei.mvp.imageloader.strategy.GlideImageLoaderStrategy;

/**
 * Created by runla on 2018/10/29.
 * 文件描述：
 */

public class ImageLoaderUtils {
    public static final int BLUR_VALUE = 10; //模糊
    public static final int CORNER_RADIUS = 10; //圆角
    public static final int MARGIN = 5;  //边距


    private static BaseImageLoaderStrategy sImageLoaderStrategy;
    private volatile static ImageLoaderUtils instance;
    /**
     * 默认参数配置
     */
    private static ImageLoaderConfig mDefaultConfig = new ImageLoaderConfig.Builder()
            .setMaxDishCache(1024 * 1024 * 50)
            .setMaxMemoryCache(1024 * 1024 * 10)
            .setErrorPicRes(R.mipmap.default_image)
            .setPlacePicRes(R.mipmap.default_image)
            .create();

    public static void init() {
        sImageLoaderStrategy = new GlideImageLoaderStrategy();
        sImageLoaderStrategy.setLoaderConfig(mDefaultConfig);
    }

    public static void setStrategy(BaseImageLoaderStrategy strategy){
        sImageLoaderStrategy = strategy;
        sImageLoaderStrategy.setLoaderConfig(mDefaultConfig);
    }

    public static void setImageConfig(ImageLoaderConfig config) {
        sImageLoaderStrategy.setLoaderConfig(config);
    }

    /**
     * 默认加载图片的形式
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadImage(Context context, ImageView imageView, Object imgUrl){
        sImageLoaderStrategy.loadImage(context,imageView,imgUrl);
    }

    /**
     * 从 drawable 中加载 image
     * @param context
     * @param imageView
     * @param resId
     */
    public static void loadImageFromDrawable(Context context,ImageView imageView,int resId){
        sImageLoaderStrategy.loadImageFromDrawable(context,imageView,resId);
    }

    /**
     * 从手机本地加载图片
     * @param context
     * @param imageView
     * @param path
     */
    public static void loadImageFromLocal(Context context,ImageView imageView,String path){
        sImageLoaderStrategy.loadImageFromLocal(context,imageView,path);
    }

    /**
     * 加载 gif 图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadGifImage(Context context,ImageView imageView,Object imgUrl){
        sImageLoaderStrategy.loadGifImage(context,imageView,imgUrl);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadCircleImage(Context context, ImageView imageView, Object imgUrl){
        sImageLoaderStrategy.loadCircleImage(context,imageView,imgUrl);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param imageView
     * @param imgUrl
     * @param radius
     */
    public static void loadRoundedImage(Context context, ImageView imageView, Object imgUrl, int radius){
        sImageLoaderStrategy.loadRoundedImage(context,imageView,imgUrl,radius);
    }


    /**
     * 加载高高斯模糊的图片(模糊效果和图片显示的宽高有关系）
     * @param context
     * @param imageView
     * @param imgUrl
     * @param blurRadius
     */
    public static void loadBlurImage(Context context, ImageView imageView, Object imgUrl, int blurRadius) {
        sImageLoaderStrategy.loadBlurImage(context,imageView,imgUrl,blurRadius);
    }


    /**
     * 加载高高斯模糊的图片,默认的模糊度为：10，图片缩放比例为 1/5(模糊效果和图片显示的宽高有关系）
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadBlurImage(Context context, ImageView imageView, Object imgUrl) {
        sImageLoaderStrategy.loadBlurImage(context,imageView,imgUrl,BLUR_VALUE);
    }


    public static void loadMarkImage(Context context,ImageView imageView,Object imgUrl,int maskId){
        sImageLoaderStrategy.loadMarkImage(context,imageView,imgUrl,maskId);
    }
    /**
     * 清理内存
     * @param context
     */
    public static void clearMemory(Context context){
        sImageLoaderStrategy.clearMemory(context);
    }
}
