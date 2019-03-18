package com.umeng.umlibrary.media;

import android.content.Context;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.listener.DefaultShareListener;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaMinAPP extends UMediaBase<UMediaMinAPP> {

    private Context context;
    private ShareAction shareAction;
    private UMMin umMin;

    /**
     * 兼容低版本的网页链接
     * 目前只有微信好友支持小程序分享，朋友圈，收藏及其他平台暂不支持
     * 小程序需要和应用在同一个微信开发平台上
     * 需要设置 标题、描述、缩略图
     *
     * @param context
     * @param shareAction
     * @param url
     */
    public UMediaMinAPP(Context context, ShareAction shareAction, String url) {
        this.context = context;
        this.shareAction = shareAction;
        this.umMin = new UMMin(url);
    }

    /**
     * 小程序页面路径
     * "pages/page10007/xxxxxx"
     *
     * @param path
     * @return
     */
    public UMediaMinAPP setPath(String path) {
        umMin.setPath(path);
        return this;
    }

    /**
     * 小程序原始id,在微信平台查询
     * "gh_xxxxxxxxxxxx"
     *
     * @param userName
     * @return
     */
    public UMediaMinAPP setUserName(String userName) {
        umMin.setUserName(userName);
        return this;
    }

    private void initMedia() {
        if (mThumbResource != 0) {
            umMin.setThumb(new UMImage(context, mThumbResource));
        }
        if (mThumbUrl != null) {
            umMin.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mTitle != null) {
            umMin.setTitle(mTitle);
        }
        if (mDescription != null) {
            umMin.setDescription(mDescription);
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
                .withMedia(umMin)
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
