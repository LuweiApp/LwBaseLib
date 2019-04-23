package com.umeng.umlibrary.media;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaText extends UMediaBase<UMediaText> {

    private String text;

    /**
     * 纯文本分享
     */
    public UMediaText(String text) {
        this.text = text;
    }

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        shareAction.withText(text);
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        super.share(activity, platform, shareAction);
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
