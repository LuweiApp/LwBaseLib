package cn.luwei.mvp.imageloader.strategy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;

import cn.luwei.mvp.R;
import cn.luwei.mvp.imageloader.ImageLoaderConfig;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by runla on 2018/10/29.
 * 文件描述：Glide 具体策略实现
 * 参考：https://github.com/wasabeef/glide-transformations/blob/master/example/src/main/java/jp/wasabeef/example/glide/MainAdapter.java
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    public static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小


    private ImageLoaderConfig imageLoaderConfig;
    private RequestOptions normalOptions;
    private RequestOptions gifOptions;
    private RequestOptions circleOptions;

    /**
     * 初始化默认加载方式的配置
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public RequestOptions getNormalOptions() {
        if (normalOptions == null) {
            normalOptions = new RequestOptions()
                    .error(imageLoaderConfig.getErrorPicRes())
                    .placeholder(imageLoaderConfig.getPlacePicRes())
                    //下载的优先级
                    .priority(Priority.NORMAL)
                    // 缓存策略
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        return normalOptions;
    }

    /**
     * 初始化 gif 加载方式的配置
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public RequestOptions getGifOptions() {
        if (gifOptions == null) {
            gifOptions = new RequestOptions()
                    .error(imageLoaderConfig.getErrorPicRes())
                    .placeholder(imageLoaderConfig.getPlacePicRes())
                    //下载的优先级
                    .priority(Priority.NORMAL)
                    // 缓存策略
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        return gifOptions;
    }

    /**
     * 设置圆形图片加载方式的配置
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public RequestOptions getCircleOptions() {
        if (circleOptions == null) {
            circleOptions = new RequestOptions()
                    .error(imageLoaderConfig.getErrorPicRes())
                    .placeholder(imageLoaderConfig.getPlacePicRes())
                    .circleCrop()
                    //下载的优先级
                    .priority(Priority.NORMAL)
                    // 缓存策略
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        return circleOptions;
    }

    @Override
    public void setLoaderConfig(ImageLoaderConfig config) {
        this.imageLoaderConfig = config;
    }

    @Override
    public void loadImage(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .apply(getNormalOptions())
                //先加载缩略图 然后在加载全图
                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadImageFromDrawable(Context context, ImageView imageView, int resId) {
        Glide.with(context)
                .load(resId)
                .apply(getNormalOptions())
                //先加载缩略图 然后在加载全图
                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadImageFromLocal(Context context, ImageView imageView, String path) {
        File file = new File(path);
        Glide.with(context)
                .load(file)
                .apply(getNormalOptions())
                //先加载缩略图 然后在加载全图
                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context)
                .asGif()
                .load(imgUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(getGifOptions())
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Context context, ImageView imageView, Object imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .apply(getCircleOptions())
                //先加载缩略图 然后在加载全图
//                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadRoundedImage(Context context, ImageView imageView, Object imgUrl, int radius) {
        Glide.with(context)
                .load(imgUrl)
                .apply(getNormalOptions())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
//                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadBlurImage(Context context, ImageView imageView, Object imgUrl, int blurRadius) {
        Glide.with(context)
                .load(imgUrl)
                .apply(getNormalOptions())
                .apply(bitmapTransform(new BlurTransformation(blurRadius, 5)))
                .thumbnail(THUMB_SIZE)
                .into(imageView);
    }

    @Override
    public void loadMarkImage(Context context, ImageView imageView, Object imgUrl,int maskId) {
        Glide.with(context)
                .load(imgUrl)
                .apply(getNormalOptions())
                .apply(bitmapTransform(new MaskTransformation(maskId)))
                .thumbnail(THUMB_SIZE)
                .into(imageView);

    }

    @Override
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }
}
