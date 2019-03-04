package com.umeng.umlibrary.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaImage extends UMediaBase<UMediaImage> {

    private final Context context;
    private final ShareAction shareAction;
    private UMImage umImage;

    /**
     * 添加网络图片
     * 分享的图片需要设置缩略图
     *
     * @param context
     * @param shareAction
     * @param url
     */
    public UMediaImage(Context context, ShareAction shareAction, String url) {
        this.context = context;
        this.shareAction = shareAction;
        this.umImage = new UMImage(context, url);
    }

    /**
     * 添加资源文件
     * @param context
     * @param shareAction
     * @param resource
     */
    public UMediaImage(Context context, ShareAction shareAction, int resource) {
        this.context = context;
        this.shareAction = shareAction;
        this.umImage = new UMImage(context, resource);
    }

    /**
     * 设置图片格式
     *
     * @param format
     * @return
     */
    public UMediaImage setCompressFormat(Bitmap.CompressFormat format) {
        umImage.compressFormat = format;
        return this;
    }

    /**
     * 设置图片压缩 用户设置的图片大小最好不要超过250k，缩略图不要超过18k
     * 如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩
     * UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
     * UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享压缩格式设置
     *
     * @param style
     * @return
     */
    public UMediaImage setCompressStyle(UMImage.CompressStyle style) {
        umImage.compressStyle = style;
        return this;
    }

    private void initMedia() {
        if (mThumbResource != 0) {
            umImage.setThumb(new UMImage(context, mThumbResource));
        }
        if (mThumbUrl != null) {
            umImage.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mTitle != null) {
            umImage.setTitle(mTitle);
        }
        if (mDescription != null) {
            umImage.setDescription(mDescription);
        }
    }

    public void share(SHARE_MEDIA platform) {
        initMedia();
        shareAction.setPlatform(platform)
                .withMedia(umImage)
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
