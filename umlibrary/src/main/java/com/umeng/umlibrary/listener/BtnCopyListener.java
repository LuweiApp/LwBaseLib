package com.umeng.umlibrary.listener;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;

/**
 * @author LiCheng
 * @date 2019/5/4
 * 分享面板上，【复制链接】按钮的操作监听
 */
public interface BtnCopyListener {
    void onBtnClick(SnsPlatform snsPlatform, SHARE_MEDIA platform);
}
