package com.umeng.umlibrary.media;

import android.content.Context;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.listener.DefaultShareListener;

/**
 * @author LiCheng
 * @date 2019/3/1
 */
public class UMediaWeb extends UMediaBase<UMediaWeb> {
    private Context context;
    private ShareAction shareAction;
    private UMWeb umWeb;

    /**
     * 分享链接
     * 需要设置 标题、描述、缩略图
     *
     * @param context
     * @param shareAction
     * @param url
     */
    public UMediaWeb(Context context, ShareAction shareAction, String url) {
        this.context = context;
        this.shareAction = shareAction;
        this.umWeb = new UMWeb(url);
    }

    private void initMedia() {
        if (mThumbFile != null) {
            umWeb.setThumb(new UMImage(context, mThumbFile));
        }
        if (mThumbBytes != null) {
            umWeb.setThumb(new UMImage(context, mThumbBytes));
        }
        if (mThumbBitmap != null) {
            umWeb.setThumb(new UMImage(context, mThumbBitmap));
        }
        if (mThumbUrl != null) {
            umWeb.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mThumbResource != 0) {
            umWeb.setThumb(new UMImage(context, mThumbResource));
        }
        if (mTitle != null) {
            umWeb.setTitle(mTitle);
        }
        if (mDescription != null) {
            umWeb.setDescription(mDescription);
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
                .withMedia(umWeb)
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
