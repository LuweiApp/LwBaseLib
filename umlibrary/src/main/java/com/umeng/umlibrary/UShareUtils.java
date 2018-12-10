package com.umeng.umlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;

/**
 * Created by LiCheng
 * Date：2018/11/29
 */
public class UShareUtils {



    private static Activity mActivity;
    private static UMShareListener mListener;
    private static String mText;
    private static UMImage mImage;
    private static UMImage mThumb;
    private static UMWeb mWeb;
    private static UMVideo mVideo;
    private static UMusic mMusic;
    private static UMEmoji mEmoji;
    private static UMMin mMinAPP;
    private static SHARE_MEDIA[] mPlatforms = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
            SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};

    public static void init(Activity activity) {
        mActivity = activity;
        if (mActivity != null) {
            //设置默认的监听
            setShareListener(new CustomShareListener(mActivity));
        }
    }

    //设置分享面板 传入自定义的面板显示方式
    //详见https://developer.umeng.com/docs/66632/detail/66639
    public static void shareBoardWithConfig(ShareBoardConfig config, SHARE_TYPE shareType) {
        shareBoard(config, shareType);
    }

    public static void shareBoardAtCenter(SHARE_TYPE shareType) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setCancelButtonVisibility(true);
        shareBoard(config, shareType);
    }

    public static void shareBoardAtBottom(SHARE_TYPE shareType) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setCancelButtonVisibility(true);
        shareBoard(config, shareType);
    }

    private static void shareBoard(ShareBoardConfig config, final SHARE_TYPE shareType) {
        ShareBoardlistener boardListener = new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
                share(platform, shareType);
            }
        };
        new ShareAction(mActivity)
                .setDisplayList(mPlatforms)
                .setShareboardclickCallback(boardListener)
                .open(config);
        /*ShareAction action = new ShareAction(mActivity)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                        SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .addButton("复制文本", "复制文本", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {

                        if (snsPlatform.mShowWord.equals("复制文本")) {
                            Toast.makeText(mActivity, "复制文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("复制链接")) {
                            Toast.makeText(mActivity, "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else {
                            new ShareAction(mActivity).withMedia(mWeb)
                                    .setPlatform(platform)
                                    .setCallback(mListener)
                                    .share();
                        }
                    }
                });
        action.open(config);*/
    }

    //设置分享面板要分享的内容
    public static void setBoardPlatforms(SHARE_MEDIA[] platforms) {
        mPlatforms = platforms;
    }


    //设置分享监听 传入自定义的监听方式
    public static void setShareListener(UMShareListener listener) {
        mListener = listener;
    }

    //创建待分享的图片
    public static void createImage(int resId) {
        mImage = new UMImage(mActivity, resId);
    }

    public static void createImage(File file) {
        mImage = new UMImage(mActivity, file);
    }

    public static void createImage(String imageUrl) {
        mImage = new UMImage(mActivity, imageUrl);
    }

    public static void createImage(Bitmap bitmap) {
        mImage = new UMImage(mActivity, bitmap);
    }

    public static void createImage(byte[] bytes) {
        mImage = new UMImage(mActivity, bytes);
    }

    //创建待分享所用的缩略图
    public static void createThumbImage(int thumbResId) {
        mThumb = new UMImage(mActivity, thumbResId);
    }

    public static void createThumbImage(File thumbFile) {
        mThumb = new UMImage(mActivity, thumbFile);
    }

    public static void createThumbImage(String thumbImageUrl) {
        mThumb = new UMImage(mActivity, thumbImageUrl);
    }

    public static void createThumbImage(Bitmap thumbBitmap) {
        mThumb = new UMImage(mActivity, thumbBitmap);
    }

    public static void createThumbImage(byte[] thumbBytes) {
        mThumb = new UMImage(mActivity, thumbBytes);
    }

    //创建待分享的文本
    public static void createText(String text) {
        mText = text;
    }

    //创建待分享的链接 需要缩略图
    public static void createUrl(String url, String title, String description) {
        mWeb = new UMWeb(url);
        mWeb.setTitle(title);
        mWeb.setDescription(description);
        mWeb.setThumb(mThumb);
    }

    //创建待分享的网络视频 需要缩略图
    public static void createVideo(String videoUrl, String title, String description) {
        mVideo = new UMVideo(videoUrl);
        mVideo.setThumb(mThumb);
        mVideo.setTitle(title);
        mVideo.setDescription(description);
    }

    //创建待分享的网络音乐 需要缩略图
    public static void createMusic(String musicUrl, String targetUrl,
                                   String title, String description) {
        mMusic = new UMusic(musicUrl);
        mMusic.setThumb(mThumb);
        mMusic.setTitle(title);
        mMusic.setDescription(description);
        mMusic.setmTargetUrl(targetUrl);
    }

    //创建待分享的表情 仅限微信好友 需要缩略图
    public static void createEmoji(String emojiUrl) {
        mEmoji = new UMEmoji(mActivity, emojiUrl);
        mEmoji.setThumb(mThumb);
    }

    //创建待分享的小程序 仅限微信好友 需要缩略图
    public static void createMinApp(String url, String title, String description,
                                    String path, String userName) {
        mMinAPP = new UMMin(url);
        mMinAPP.setThumb(mThumb);
        mMinAPP.setTitle(title);
        mMinAPP.setDescription(description);
        mMinAPP.setUserName(userName);
        mMinAPP.setPath(path);
    }


    public static void share(SHARE_MEDIA platform, SHARE_TYPE shareType) {
        ShareAction action = new ShareAction(mActivity)
                .setPlatform(platform).setCallback(mListener);
        switch (shareType) {
            case SHARE_TYPE_TEXT:
                action.withText(mText).share();
                break;
            case SHARE_TYPE_IMAGE:
                action.withMedia(mImage).share();
                break;
            case SHARE_TYPE_TEXTANDIMG:
                action.withText(mText).withMedia(mImage).share();
                break;
            case SHARE_TYPE_WEB:
                action.withMedia(mWeb).share();
                break;
            case SHARE_TYPE_VIDEO:
                action.withMedia(mVideo).share();
                break;
            case SHARE_TYPE_MUSIC:
                action.withMedia(mMusic).share();
                break;
            case SHARE_TYPE_EMOJI:
                action.withMedia(mEmoji).share();
                break;
            case SHARE_TYPE_MINAPP:
                action.withMedia(mMinAPP).share();
                break;
        }
    }

}
