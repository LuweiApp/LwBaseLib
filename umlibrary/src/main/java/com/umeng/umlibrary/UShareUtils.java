package com.umeng.umlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.umlibrary.listener.DefaultShareListener;
import com.umeng.umlibrary.media.UMediaGif;
import com.umeng.umlibrary.media.UMediaImage;
import com.umeng.umlibrary.media.UMediaMinAPP;
import com.umeng.umlibrary.media.UMediaMusic;
import com.umeng.umlibrary.media.UMediaText;
import com.umeng.umlibrary.media.UMediaVideo;
import com.umeng.umlibrary.media.UMediaWeb;

/**
 * @author LiCheng
 * @date 2019/3/1
 */
public class UShareUtils {

    @SuppressLint("StaticFieldLeak")
    private static volatile UShareUtils singleton = null;
    private final Context context;
    private ShareAction shareAction;

    private UShareUtils(Context context, ShareAction shareAction) {
        this.context = context;
        this.shareAction = shareAction;
    }

    public static UShareUtils with(Activity activity) {
        if (singleton == null) {
            synchronized (UShareUtils.class) {
                if (singleton == null) {
                    singleton = new Builder(activity).build();
                }
            }
        }
        return singleton;
    }

    /**
     * 纯文本分享
     *
     * @param text
     * @return
     */
    public UMediaText createText(String text) {
        return new UMediaText(context, shareAction, text);
    }

    /**
     * 网络图片分享
     *
     * @param imgUrl
     * @return
     */
    public UMediaImage createImage(String imgUrl) {
        return new UMediaImage(context, shareAction, imgUrl);
    }

    /**
     * 资源文件分享
     *
     * @param resource
     * @return
     */
    public UMediaImage createImage(int resource) {
        return new UMediaImage(context, shareAction, resource);
    }

    /**
     * 分享链接
     *
     * @param webUrl
     * @return
     */
    public UMediaWeb createWeb(String webUrl) {
        return new UMediaWeb(context, shareAction, webUrl);
    }

    /**
     * 分享网络视频
     *
     * @param videoUrl
     * @return
     */
    public UMediaVideo createVideo(String videoUrl) {
        return new UMediaVideo(context, shareAction, videoUrl);
    }

    /**
     * 分享网络音乐
     *
     * @param musicUrl
     * @return
     */
    public UMediaMusic createMusic(String musicUrl) {
        return new UMediaMusic(context, shareAction, musicUrl);
    }

    /**
     * 分享动图表情
     * 仅支持分享到微信好友
     *
     * @param gifUrl
     * @return
     */
    public UMediaGif createGif(String gifUrl) {
        return new UMediaGif(context, shareAction, gifUrl);
    }

    /**
     * 分享小程序
     * 仅支持分享到微信好友
     * 小程序需要和应用在同一个微信开发平台上
     *
     * @param minAPPUrl
     * @return
     */
    public UMediaMinAPP createMinApp(String minAPPUrl) {
        return new UMediaMinAPP(context, shareAction, minAPPUrl);
    }


    /**
     * 构建默认ShareAction
     */
    public static class Builder {
        private final Activity activity;
        private UMShareListener shareListener;
        private ShareAction shareAction;
        private SHARE_MEDIA[] platforms;

        /**
         * 开始构造实例
         */
        public Builder(Activity activity) {
            if (activity == null) {
                throw new IllegalArgumentException("Aitivity must not be null.");
            }
            this.activity = activity;
        }


        public Builder setShareListener(UMShareListener shareListener) {
            if (shareListener == null) {
                throw new IllegalArgumentException("UMShareListener must not be null.");
            }
            if (this.shareListener != null) {
                throw new IllegalStateException("UMShareListener already set.");
            }
            this.shareListener = shareListener;
            return this;
        }

        public Builder setDisplayList(SHARE_MEDIA... platforms) {
            if (platforms == null) {
                throw new IllegalArgumentException("platforms must not be null.");
            }
            if (this.platforms != null) {
                throw new IllegalStateException("platforms already set.");
            }
            this.platforms = platforms;
            return this;
        }


        public UShareUtils build() {
            Context context = activity.getBaseContext();
            if (shareListener == null) {
                //添加默认分享监听
                shareListener = new DefaultShareListener(context);
            }

            if (platforms == null) {
                //添加微信微博QQ三大平台
                platforms = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                        SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};
//                platforms = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN,
//                        SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA};
            }

            if (shareAction == null) {
                shareAction = new ShareAction(activity)
                        .setCallback(shareListener)
                        .setDisplayList(platforms);
            }
            return new UShareUtils(context, shareAction);
        }
    }
}

