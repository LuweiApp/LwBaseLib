package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMVideo;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaVideo extends UMediaBase<UMediaVideo> {

    private UMVideo umVideo;

    /**
     * 视频只能使用网络视频
     * 需要设置 标题、描述、缩略图
     */
    public UMediaVideo(String url) {
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

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        shareAction.withMedia(getMedia(umVideo, activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaVideo setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaVideo setDescription(String description) {
        return super.setDescription(description);
    }

    @Override
    public void shareWithBottomBoard(Activity activity) {
        super.shareWithBottomBoard(activity);
    }

    @Override
    public void share(Activity activity, SHARE_MEDIA platform) {
        super.share(activity, platform);
    }

}
