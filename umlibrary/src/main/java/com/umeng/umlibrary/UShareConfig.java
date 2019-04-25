package com.umeng.umlibrary;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * @author LiCheng
 * @date 2019/4/22
 * 配置分享
 */
public class UShareConfig {

    /**
     * 分享出去的监听
     */
    private UMShareListener shareListener;

    /**
     * 分享平台
     */
    private SHARE_MEDIA[] platforms;

    private UMImage thumb;

    private String title;

    private String description;


    public UShareConfig(Builder builder) {
        this.shareListener = builder.shareListener;
        this.platforms = builder.platforms;
    }

    public UMShareListener getShareListener() {
        return shareListener;
    }

    public SHARE_MEDIA[] getPlatforms() {
        return platforms;
    }

    public void setShareListener(UMShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public void setPlatforms(SHARE_MEDIA[] platforms) {
        this.platforms = platforms;
    }

    public UMImage getThumb() {
        return thumb;
    }

    public void setThumb(UMImage thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static class Builder {
        private UMShareListener shareListener;
        private SHARE_MEDIA[] platforms;

        public Builder setShareListener(UMShareListener shareListener) {
            if (shareListener == null) {
                throw new IllegalArgumentException("UMShareListener must not be null.");
            }
            this.shareListener = shareListener;
            return this;
        }

        public Builder setDisplayList(SHARE_MEDIA... platforms) {
            if (platforms == null) {
                throw new IllegalArgumentException("platforms must not be null.");
            }
            this.platforms = platforms;
            return this;
        }

        public UShareConfig create() {
            return new UShareConfig(this);
        }
    }
}
