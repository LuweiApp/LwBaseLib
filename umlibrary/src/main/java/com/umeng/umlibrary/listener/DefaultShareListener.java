package com.umeng.umlibrary.listener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

/**
 * Created by LiCheng
 * Date：2018/11/28
 */
public class DefaultShareListener implements UMShareListener {
    private WeakReference<Context> mContext;
    private SimpleShareListener mSimpleShareListener;

    public DefaultShareListener(Context context) {
        mContext = new WeakReference(context);
    }

    public DefaultShareListener(Context context, SimpleShareListener simpleShareListener) {
        this.mContext = new WeakReference(context);
        this.mSimpleShareListener = simpleShareListener;
    }

    @Override
    public void onStart(SHARE_MEDIA platform) {

    }

    @Override
    public void onResult(SHARE_MEDIA platform) {

        if (platform.name().equals("WEIXIN_FAVORITE")) {
            Toast.makeText(mContext.get(), "微信收藏完成", Toast.LENGTH_SHORT).show();
        } else if (platform.name().equals("WEIXIN") || platform.name().equals("WEIXIN_CIRCLE")) {
            Toast.makeText(mContext.get(), "微信分享完成", Toast.LENGTH_SHORT).show();
        } else if (platform.name().equals("SINA")) {
            Toast.makeText(mContext.get(), "微博分享成功", Toast.LENGTH_SHORT).show();
        } else if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                && platform != SHARE_MEDIA.EMAIL
                && platform != SHARE_MEDIA.FLICKR
                && platform != SHARE_MEDIA.FOURSQUARE
                && platform != SHARE_MEDIA.TUMBLR
                && platform != SHARE_MEDIA.POCKET
                && platform != SHARE_MEDIA.PINTEREST
                && platform != SHARE_MEDIA.INSTAGRAM
                && platform != SHARE_MEDIA.GOOGLEPLUS
                && platform != SHARE_MEDIA.YNOTE
                && platform != SHARE_MEDIA.EVERNOTE) {
            Toast.makeText(mContext.get(), platform + "分享成功", Toast.LENGTH_SHORT).show();
        }

        if (mSimpleShareListener != null) {
            mSimpleShareListener.onComplete(platform);
        }

    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable throwable) {
        if (throwable != null) {
            Log.d("UMShareThrow", "UMShareThrow:" + throwable.getMessage());
        }
        if (platform.name().equals("WEIXIN_FAVORITE")) {
            Toast.makeText(mContext.get(), "微信收藏失败", Toast.LENGTH_SHORT).show();
        } else if (platform.name().equals("WEIXIN") || platform.name().equals("WEIXIN_CIRCLE")) {
            Toast.makeText(mContext.get(), "微信分享失败", Toast.LENGTH_SHORT).show();
        } else if (platform.name().equals("SINA")) {
            Toast.makeText(mContext.get(), "微博分享失败", Toast.LENGTH_SHORT).show();
        } else if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                && platform != SHARE_MEDIA.EMAIL
                && platform != SHARE_MEDIA.FLICKR
                && platform != SHARE_MEDIA.FOURSQUARE
                && platform != SHARE_MEDIA.TUMBLR
                && platform != SHARE_MEDIA.POCKET
                && platform != SHARE_MEDIA.PINTEREST
                && platform != SHARE_MEDIA.INSTAGRAM
                && platform != SHARE_MEDIA.GOOGLEPLUS
                && platform != SHARE_MEDIA.YNOTE
                && platform != SHARE_MEDIA.EVERNOTE) {
            Toast.makeText(mContext.get(), platform + "分享失败", Toast.LENGTH_SHORT).show();

        }

        if (mSimpleShareListener != null) {
            mSimpleShareListener.onError(platform, throwable);
        }

    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Toast.makeText(mContext.get(), "分享取消", Toast.LENGTH_SHORT).show();
    }
}
