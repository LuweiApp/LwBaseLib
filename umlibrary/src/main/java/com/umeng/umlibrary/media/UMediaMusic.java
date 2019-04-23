package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMusic;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaMusic extends UMediaBase<UMediaMusic> {

    private UMusic uMusic;

    /**
     * 音乐的播放链接 只能使用网络音乐
     * 播放链接是指在微信qq分享音乐，是可以在当前聊天界面播放的
     * 要求这个musicurl（播放链接）必须要以.mp3等音乐格式结尾
     * 需要设置 标题、描述、缩略图
     */
    public UMediaMusic(String url) {
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

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        shareAction.withMedia(getMedia(uMusic, activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaMusic setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaMusic setDescription(String description) {
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
