package com.umeng.umlibrary.media;

import android.content.Context;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.listener.DefaultShareListener;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaVideo extends UMediaBase<UMediaVideo> {
    private Context context;
    private ShareAction shareAction;
    private UMVideo umVideo;

    /**
     * 视频只能使用网络视频
     * 需要设置 标题、描述、缩略图
     *
     * @param context
     * @param shareAction
     * @param url
     */
    public UMediaVideo(Context context, ShareAction shareAction, String url) {
        this.context = context;
        this.shareAction = shareAction;
        this.umVideo = new UMVideo(url);
    }

    public UMediaVideo setDuration(int duration) {
        umVideo.setDuration(duration);
        return this;
    }

    public UMediaVideo setH5Url(String h5Url) {
        umVideo.setH5Url(h5Url);
        return this;
    }

    public UMediaVideo setHighBandDataUrl(String highBandDataUrl) {
        umVideo.setHighBandDataUrl(highBandDataUrl);
        return this;
    }

    public UMediaVideo setLowBandDataUrl(String lowBandDataUrl) {
        umVideo.setLowBandDataUrl(lowBandDataUrl);
        return this;
    }

    public UMediaVideo setLowBandUrl(String lowBandUrl) {
        umVideo.setLowBandUrl(lowBandUrl);
        return this;
    }

    private void initMedia() {
        if (mThumbResource != 0) {
            umVideo.setThumb(new UMImage(context, mThumbResource));
        }
        if (mThumbUrl != null) {
            umVideo.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mTitle != null) {
            umVideo.setTitle(mTitle);
        }
        if (mDescription != null) {
            umVideo.setDescription(mDescription);
        }
    }

    public void share(SHARE_MEDIA platform) {
        initMedia();
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        if (mSimpleShareListener != null) {
            shareAction.setCallback(new DefaultShareListener(context, mSimpleShareListener));
        }
        if (mCustomShareListener != null) {
            shareAction.setCallback(mCustomShareListener);
        }
        shareAction.setPlatform(platform)
                .withMedia(umVideo)
                .share();
    }

    public void shareWithCenterBoard() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setIndicatorVisibility(false);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        config.setTitleTextColor(Color.BLACK);
        shareWithCustomBoard(config);
    }

    public void shareWithBottomBoard() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setCancelButtonVisibility(false);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        shareWithCustomBoard(config);
    }

    public void shareWithCustomBoard(ShareBoardConfig config) {
        if (mPlatforms != null) {
            shareAction.setDisplayList(mPlatforms);
        }
        if (mIsBoardOnlyShowWechatAndSina) {
            shareAction.setDisplayList(SHARE_MEDIA.WEIXIN,
                    SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.SINA);
        }
        shareAction.setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
                share(platform);
            }
        }).open(config);
    }
}
