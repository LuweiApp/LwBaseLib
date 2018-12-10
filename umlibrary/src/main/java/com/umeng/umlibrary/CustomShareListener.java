package com.umeng.umlibrary;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

/**
 * Created by LiCheng
 * Date：2018/11/28
 */
public class CustomShareListener implements UMShareListener {
    private WeakReference<Activity> mActivity;

    public CustomShareListener(Activity activity) {
        mActivity = new WeakReference(activity);
    }

    @Override
    public void onStart(SHARE_MEDIA platform) {

    }

    @Override
    public void onResult(SHARE_MEDIA platform) {

        if (platform.name().equals("WEIXIN_FAVORITE")) {
            Toast.makeText(mActivity.get(), platform + " 收藏完成", Toast.LENGTH_SHORT).show();
        } else if(platform.name().equals("WEIXIN")||platform.name().equals("WEIXIN_CIRCLE")){
            Toast.makeText(mActivity.get(), platform + " 分享完成", Toast.LENGTH_SHORT).show();
        }else  {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
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
                Toast.makeText(mActivity.get(), platform + " 分享成功", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable throwable) {
        if (throwable != null) {
            Log.d("throw", "throw:" + throwable.getMessage());
        }
        if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
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
            Toast.makeText(mActivity.get(), platform + " 分享失败", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Toast.makeText(mActivity.get(), platform + " 分享取消", Toast.LENGTH_SHORT).show();
    }
}
