package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaGif extends UMediaBase<UMediaGif> {

    private String umGifUrl;

    /**
     * GIF链接
     * 目前只有微信好友分享支持Emoji表情，其他平台暂不支持
     */
    public UMediaGif(String url) {
        this.umGifUrl = url;
    }

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        UMEmoji umGif = new UMEmoji(activity, umGifUrl);
        shareAction.withMedia(getMedia(umGif, activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaGif setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaGif setDescription(String description) {
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
