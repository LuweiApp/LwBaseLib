package com.umeng.umlibrary.listener;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * @author LiCheng
 * @date 2019/3/18
 * 简单的分享监听
 */
public interface SimpleShareListener {

    void onComplete(SHARE_MEDIA platform);

    void onError(SHARE_MEDIA platform, Throwable throwable);
}
