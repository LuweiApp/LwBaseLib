package com.umeng.umlibrary.media;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.UShareUtils;
import com.umeng.umlibrary.listener.DefaultShareListener;
import com.umeng.umlibrary.listener.SimpleShareListener;

import java.io.File;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaBase<T extends UMediaBase> {
    byte[] mThumbBytes;
    Bitmap mThumbBitmap;
    File mThumbFile;
    int mThumbResource;
    String mThumbUrl;
    String mWithText;
    String mTitle;
    String mDescription;
    SHARE_MEDIA[] mPlatforms;
    SimpleShareListener mSimpleShareListener;
    UMShareListener mCustomShareListener;


    public T setThumb(String thumbUrl) {
        mThumbUrl = thumbUrl;
        return (T) this;
    }

    public T setThumb(int thumbResource) {
        mThumbResource = thumbResource;
        return (T) this;
    }

    public T setThumb(Bitmap thumbBitmap) {
        mThumbBitmap = thumbBitmap;
        return (T) this;
    }

    public T setThumb(File thumbFile) {
        mThumbFile = thumbFile;
        return (T) this;
    }

    public T setThumb(byte[] thumbBytes) {
        mThumbBytes = thumbBytes;
        return (T) this;
    }

    /**
     * 添加标题
     *
     * @param title
     * @return
     */
    public T setTitle(String title) {
        mTitle = title;
        return (T) this;
    }

    /**
     * 添加描述
     *
     * @param description
     * @return
     */
    public T setDescription(String description) {
        mDescription = description;
        return (T) this;
    }

    /**
     * 带上文字
     *
     * @param withText
     * @return
     */
    public T withText(String withText) {
        mWithText = withText;
        return (T) this;
    }

    /**
     * 设置分享面板包含的平台
     *
     * @param platforms
     * @return
     */
    public T setBoardDisplayList(SHARE_MEDIA... platforms) {
        mPlatforms = platforms;
        return (T) this;
    }


    /**
     * 设置简单的分享结果监听
     *
     * @param simpleShareListener
     * @return
     */
    public T setSimpleShareListener(SimpleShareListener simpleShareListener) {
        mSimpleShareListener = simpleShareListener;
        return (T) this;
    }

    /**
     * 设置完成的分享结果监听
     *
     * @param customShareListener
     * @return
     */
    public T setCustomShareListener(UMShareListener customShareListener) {
        mCustomShareListener = customShareListener;
        return (T) this;
    }

    /**
     * 以显示在中部的分享面板来分享
     */
    public void shareWithCenterBoard(Activity activity) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setIndicatorVisibility(false);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        config.setTitleTextColor(Color.BLACK);
        shareWithCustomBoard(activity, config);
    }

    /**
     * 以显示在底部的分享面板来分享
     */
    public void shareWithBottomBoard(Activity activity) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setCancelButtonVisibility(false);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        shareWithCustomBoard(activity, config);
    }

    /**
     * 自定义分享面板分享
     */
    public void shareWithCustomBoard(final Activity activity, ShareBoardConfig config) {
        final ShareAction shareAction = new ShareAction(activity);
        shareAction.setDisplayList(getPlatforms());
        shareAction.setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
                share(activity, platform, shareAction);
            }
        }).open(config);
    }

    /**
     * 直接分享到某平台
     */
    public void share(Activity activity, SHARE_MEDIA platform) {
        share(activity, platform, new ShareAction(activity));
    }

    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        shareAction.setPlatform(platform)
                .setCallback(getListener(activity))
                .share();
    }

    private SHARE_MEDIA[] getPlatforms() {
        //如果有设置面板分享平台则返回
        if (mPlatforms != null) {
            return mPlatforms;
        }
        //否则返回全局设置的参数
        if (UShareUtils.getConfig().getPlatforms() != null) {
            return UShareUtils.getConfig().getPlatforms();
        }
        //以上都没有则返回默认分享平台
        return new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE};
    }

    /**
     * 获取分享监听
     */
    private UMShareListener getListener(Activity activity) {
        //设置完整的自定义监听 优先级最高
        if (mCustomShareListener != null) {
            return mCustomShareListener;
        }
        //设置简单分享监听 只有完成和失败 优先级中
        if (mSimpleShareListener != null) {
            return new DefaultShareListener(activity, mSimpleShareListener);
        }
        //根据全局配置设置监听 优先级最低
        if (UShareUtils.getConfig().getShareListener() != null) {
            return UShareUtils.getConfig().getShareListener();
        }
        //如果以上都没有 则返回默认的监听器
        return new DefaultShareListener(activity);
    }

    /**
     * 为Media添加缩略图、标题和描述
     */
    <M extends BaseMediaObject> M getMedia(M m, Context context) {
        if (UShareUtils.getConfig().getThumb() != null) {
            m.setThumb(UShareUtils.getConfig().getThumb());
        }
        if (UShareUtils.getConfig().getTitle() != null) {
            m.setTitle(UShareUtils.getConfig().getTitle());
        }
        if (UShareUtils.getConfig().getDescription() != null) {
            m.setDescription(UShareUtils.getConfig().getDescription());
        }
        if (mThumbFile != null) {
            m.setThumb(new UMImage(context, mThumbFile));
        }
        if (mThumbBytes != null) {
            m.setThumb(new UMImage(context, mThumbBytes));
        }
        if (mThumbBitmap != null) {
            m.setThumb(new UMImage(context, mThumbBitmap));
        }
        if (mThumbUrl != null) {
            m.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mThumbResource != 0) {
            m.setThumb(new UMImage(context, mThumbResource));
        }
        if (mTitle != null) {
            m.setTitle(mTitle);
        }
        if (mDescription != null) {
            m.setDescription(mDescription);
        }
        return m;
    }

}
