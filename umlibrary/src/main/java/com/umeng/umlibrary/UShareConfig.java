package com.umeng.umlibrary;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

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
