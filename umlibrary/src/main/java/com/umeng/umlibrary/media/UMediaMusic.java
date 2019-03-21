package com.umeng.umlibrary.media;

import android.content.Context;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.listener.DefaultShareListener;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaMusic extends UMediaBase<UMediaMusic> {
    private Context context;
    private ShareAction shareAction;
    private UMusic uMusic;

    /**
     * 音乐的播放链接 只能使用网络音乐
     * 播放链接是指在微信qq分享音乐，是可以在当前聊天界面播放的
     * 要求这个musicurl（播放链接）必须要以.mp3等音乐格式结尾
     * 需要设置 标题、描述、缩略图
     *
     * @param context
     * @param shareAction
     * @param url
     */
    public UMediaMusic(Context context, ShareAction shareAction, String url) {
        this.context = context;
        this.shareAction = shareAction;
        this.uMusic = new UMusic(url);
    }

    /**
     * 跳转链接是指点击linkcard之后进行跳转的链接
     *
     * @param targetUrl
     * @return
     */
    public UMediaMusic setTargetUrl(String targetUrl) {
        uMusic.setmTargetUrl(targetUrl);
        return this;
    }

    public UMediaMusic setDuration(int duration) {
        uMusic.setDuration(duration);
        return this;
    }

    public UMediaMusic setH5Url(String h5Url) {
        uMusic.setH5Url(h5Url);
        return this;
    }

    public UMediaMusic setHighBandDataUrl(String highBandDataUrl) {
        uMusic.setHighBandDataUrl(highBandDataUrl);
        return this;
    }

    public UMediaMusic setLowBandDataUrl(String lowBandDataUrl) {
        uMusic.setLowBandDataUrl(lowBandDataUrl);
        return this;
    }

    public UMediaMusic setLowBandUrl(String lowBandUrl) {
        uMusic.setLowBandUrl(lowBandUrl);
        return this;
    }

    private void initMedia() {
        if (mThumbFile != null) {
            uMusic.setThumb(new UMImage(context, mThumbFile));
        }
        if (mThumbBytes != null) {
            uMusic.setThumb(new UMImage(context, mThumbBytes));
        }
        if (mThumbBitmap != null) {
            uMusic.setThumb(new UMImage(context, mThumbBitmap));
        }
        if (mThumbUrl != null) {
            uMusic.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mThumbResource != 0) {
            uMusic.setThumb(new UMImage(context, mThumbResource));
        }
        if (mTitle != null) {
            uMusic.setTitle(mTitle);
        }
        if (mDescription != null) {
            uMusic.setDescription(mDescription);
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
                .withMedia(uMusic)
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
