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
     * 默认分享监听
     */
    private UMShareListener shareListener;

    /**
     * 默认分享平台
     */
    private SHARE_MEDIA[] platforms;

    /**
     * 默认分享缩略图
     */
    private UMImage thumb;

    /**
     * 默认分享标题
     */
    private String title;

    /**
     * 默认分享描述
     */
    private String description;

    /**
     * 默认分享面板上复制链接按钮的Icon 使用drawable文件中的名字，比如umeng_socialize_copyurl
     */
    private String copyUrlBtnIcon;


    public UShareConfig(Builder builder) {
        this.shareListener = builder.shareListener;
        this.platforms = builder.platforms;
        this.thumb = builder.thumb;
        this.title = builder.title;
        this.description = builder.description;
        this.copyUrlBtnIcon = builder.copyUrlBtnIcon;
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

    public String getCopyUrlBtnIcon() {
        return copyUrlBtnIcon;
    }

    public void setCopyUrlBtnIcon(String copyUrlBtnIcon) {
        this.copyUrlBtnIcon = copyUrlBtnIcon;
    }


    public static class Builder {
        private UMShareListener shareListener;
        private SHARE_MEDIA[] platforms;
        private UMImage thumb;
        private String title;
        private String description;
        private String copyUrlBtnIcon;

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

        public Builder setThumb(UMImage thumb){
            this.thumb = thumb;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setCopyUrlBtnIcon(String copy_icon_text_from_drawable_xml){
            this.copyUrlBtnIcon = copy_icon_text_from_drawable_xml;
            return this;
        }

        public UShareConfig create() {
            return new UShareConfig(this);
        }
    }
}
