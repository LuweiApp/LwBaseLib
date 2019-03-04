package com.umeng.umlibrary.media;


import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaBase<T extends UMediaBase> {

    protected int mThumbResource;
    protected String mThumbUrl;
    protected String mWithText;
    protected String mTitle;
    protected String mDescription;
    protected SHARE_MEDIA[] mPlatforms;
    protected boolean mIsBoardOnlyShowWechatAndSina;

    @SuppressWarnings("unchecked")
    public T setThumb(String thumbUrl) {
        mThumbUrl = thumbUrl;
        return (T) this;
    }

    /**
     * 添加缩略图
     *
     * @param thumbResource
     * @return
     */
    public T setThumb(int thumbResource) {
        mThumbResource = thumbResource;
        return (T) this;
    }

    /**
     * 添加标题
     *
     * @param title
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setTitle(String title) {
        mTitle = title;
        return (T) this;
    }

    /**
     * 添加描述
     *
     * @param description
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setDescription(String description) {
        mDescription = description;
        return (T) this;
    }

    /**
     * 带上文字
     *
     * @param withText
     * @return
     */
    @SuppressWarnings("unchecked")
    public T withText(String withText) {
        mWithText = withText;
        return (T) this;
    }

    /**
     * 设置分享面板包含的平台
     *
     * @param platforms
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setBoardDisplayList(SHARE_MEDIA... platforms) {
        mPlatforms = platforms;
        return (T) this;
    }

    /**
     * 设置分享面板只分享微信好友、微信朋友圈和新浪
     *
     * @param isBoardOnlyShowWechatAndSina
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setBoardOnlyShowWechatAndSina(boolean isBoardOnlyShowWechatAndSina) {
        mIsBoardOnlyShowWechatAndSina = isBoardOnlyShowWechatAndSina;
        return (T) this;
    }

}
