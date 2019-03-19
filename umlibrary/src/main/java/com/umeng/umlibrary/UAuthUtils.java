package com.umeng.umlibrary;

import android.app.Activity;
import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.umlibrary.listener.SimpleAuthorizeListener;

import java.util.Map;

/**
 * @author LiCheng
 * @date 2019/3/3
 */
public class UAuthUtils {
    private static final String TAG = "UAuthUtils";
    private static Map<String, String> weChatInfoMap;

    public static void authorizeWeChat(Activity activity) {
        if (isWeChatAuthorize(activity)) {
            deleteOauth(activity);
        }
        UMShareAPI.get(activity.getBaseContext())
                .getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onStart " + "授权开始");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.d(TAG, "onComplete " + "授权完成");
                        weChatInfoMap = map;
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.d(TAG, "onError " + "授权失败" + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.d(TAG, "onCancel " + "授权取消");
                    }
                });
    }

    public static void authorizeWeChat(Activity activity, final SimpleAuthorizeListener listener) {
        if (isWeChatAuthorize(activity)) {
            deleteOauth(activity);
        }
        UMShareAPI.get(activity.getBaseContext())
                .getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onStart " + "授权开始");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.d(TAG, "onComplete " + "授权完成");
                        weChatInfoMap = map;
                        listener.onComplete(map);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.d(TAG, "onError " + "授权失败" + throwable.getMessage());
                        listener.onError(throwable);
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.d(TAG, "onCancel " + "授权取消");
                    }
                });
    }

    public static void deleteOauth(Activity activity) {
        UMShareAPI.get(activity.getBaseContext())
                .deleteOauth(activity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.d(TAG, "onStart " + "删除授权开始");
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.d(TAG, "onComplete " + "删除授权完成");
                        weChatInfoMap = map;
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Log.d(TAG, "onError " + "删除授权失败" + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Log.d(TAG, "onCancel " + "删除授权取消");
                    }
                });
    }

    public static boolean isWeChatAuthorize(Activity activity) {
        return UMShareAPI.get(activity.getBaseContext())
                .isAuthorize(activity, SHARE_MEDIA.WEIXIN);
    }

    public static Map<String, String> getWeChatInfoMap() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap;
    }

    public static String getOpenId() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("openid");
    }

    public static String getUnionId() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("unionid");
    }

    public static String getAccessToken() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("accessToken");
    }

    public static String getRefreshToken() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("refreshToken");
    }

    public static String getExpiration() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("expiration");
    }

    public static String getIconUrl() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("iconurl");
    }

    public static String getName() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("name");
    }

    public static String getGender() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("gender");
    }

    public static String getCountry() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("country");
    }

    public static String getProvince() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("province");
    }

    public static String getCity() {
        if (isInfoMapNull()) {
            throw new IllegalArgumentException("weChatInfoMap must not be null.");
        }
        return weChatInfoMap.get("city");
    }

    private static boolean isInfoMapNull() {
        return (null == weChatInfoMap || weChatInfoMap.size() == 0);
    }

}
