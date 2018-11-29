package cn.luwei.mvp.imageloader;

import android.support.annotation.IdRes;

/**
 * Created by runla on 2018/10/29.
 * 文件描述：关于图片加载器的配置，这里使用到了建造者模式
 */

public class ImageLoaderConfig {
    /**
     * 占位图
     */
    private int mPlacePicRes;
    /**
     * 错误图
     */
    private int mErrorPicRes;

    /**
     * 图片的Url
     */
    private String mImgUrl;

    /**
     * 最大磁盘缓存
     */
    private int maxDishCache;

    /**
     * 最大内存缓存
     */
    private int maxMemoryCache;

    public ImageLoaderConfig(Builder builder){
        this.maxDishCache = builder.maxDishCache;
        this.maxMemoryCache = builder.maxMemoryCache;
        this.mErrorPicRes = builder.mErrorPicRes;
        this.mImgUrl = builder.mImgUrl;
        this.mPlacePicRes = builder.mPlacePicRes;
    }

    public int getPlacePicRes() {
        return mPlacePicRes;
    }

    public int getErrorPicRes() {
        return mErrorPicRes;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public int getMaxDishCache() {
        return maxDishCache;
    }

    public int getMaxMemoryCache() {
        return maxMemoryCache;
    }

    public static class Builder{
        public int mPlacePicRes;
        public int mErrorPicRes;
        public int maxDishCache;
        public int maxMemoryCache;
        public String mImgUrl;

        public Builder setPlacePicRes(int mPlacePicRes) {
            this.mPlacePicRes = mPlacePicRes;
            return this;
        }

        public Builder setErrorPicRes(int mErrorPicRes) {
            this.mErrorPicRes = mErrorPicRes;
            return this;
        }

        public Builder setImgUrl(String mImgUrl) {
            this.mImgUrl = mImgUrl;
            return this;
        }

        public Builder setMaxDishCache(int maxDishCache) {
            this.maxDishCache = maxDishCache;
            return this;
        }

        public Builder setMaxMemoryCache(int maxMemoryCache) {
            this.maxMemoryCache = maxMemoryCache;
            return this;
        }

        public ImageLoaderConfig create(){
            return new ImageLoaderConfig(this);
        }
    }
}
