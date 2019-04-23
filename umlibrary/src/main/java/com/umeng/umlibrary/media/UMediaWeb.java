package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

/**
 * @author LiCheng
 * @date 2019/3/1
 */
public class UMediaWeb extends UMediaBase<UMediaWeb> {

    private UMWeb umWeb;

    /**
     * 分享链接
     * 需要设置 标题、描述、缩略图
     */
    public UMediaWeb(String url) {
        this.umWeb = new UMWeb(url);
    }

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        shareAction.withMedia(getMedia(umWeb, activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaWeb setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaWeb setDescription(String description) {
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
