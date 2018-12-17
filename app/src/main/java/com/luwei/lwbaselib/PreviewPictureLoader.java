package com.luwei.lwbaselib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luwei.util.imageloader.ImageLoaderUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/12
 * @Description:
 */
public class PreviewPictureLoader implements IZoomMediaLoader {

    private RequestOptions gifOptions;

    @Override
    public void displayImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull MySimpleTarget simpleTarget) {
        Glide.with(context)
                .asBitmap()
                .load(path)
                .apply(getGifOptions())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        simpleTarget.onLoadFailed(null);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        simpleTarget.onResourceReady();
                        return false;
                    }
                })
                .into(imageView);

    }

    @Override
    public void displayGifImage(@NonNull Fragment context, @NonNull String path, ImageView imageView, @NonNull MySimpleTarget simpleTarget) {
        Glide.with(context)
                .asGif()
                .load(path)
                .apply(getGifOptions())
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        simpleTarget.onLoadFailed(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        simpleTarget.onResourceReady();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void onStop(@NonNull Fragment context) {
        Glide.with(context).onStop();

    }

    @Override
    public void clearMemory(@NonNull Context c) {
        Glide.get(c).clearMemory();
    }

    public RequestOptions getGifOptions() {
        if (gifOptions == null) {
            gifOptions = new RequestOptions()
                    .error(R.mipmap.default_image)
                    //下载的优先级
                    .priority(Priority.NORMAL)
                    // 没有动画
                    .dontAnimate()
                    // 缓存策略
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }
        return gifOptions;
    }
}
