package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMMin;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaMinAPP extends UMediaBase<UMediaMinAPP> {

    private UMMin umMin;

    /**
     * 兼容低版本的网页链接
     * 目前只有微信好友支持小程序分享，朋友圈，收藏及其他平台暂不支持
     * 小程序需要和应用在同一个微信开发平台上
     * 需要设置 标题、描述、缩略图
     */
    public UMediaMinAPP(String url) {
        this.umMin = new UMMin(url);
    }

    /**
     * 小程序页面路径
     * "pages/page10007/xxxxxx"
     */
    public UMediaMinAPP setPath(String path) {
        umMin.setPath(path);
        return this;
    }

    /**
     * 小程序原始id,在微信平台查询
     * "gh_xxxxxxxxxxxx"
     */
    public UMediaMinAPP setUserName(String userName) {
        umMin.setUserName(userName);
        return this;
    }


    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        shareAction.withMedia(getMedia(umMin, activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaMinAPP setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaMinAPP setDescription(String description) {
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
