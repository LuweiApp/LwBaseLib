package com.umeng.umlibrary.media;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.umlibrary.UShareUtils;
import com.umeng.umlibrary.listener.CustomBtnListener;
import com.umeng.umlibrary.listener.DefaultShareListener;
import com.umeng.umlibrary.listener.SimpleShareListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaBase<T extends UMediaBase> {
    private static final String BTN_COPY_URL_KEY_WORD = "u_share_btn_copy";
    private static final String BTN_COPY_URL_SHOW_WORD = "复制链接";
    byte[] mThumbBytes;
    Bitmap mThumbBitmap;
    File mThumbFile;
    int mThumbResource;
    String mThumbUrl;
    String mWithText;
    String mTitle;
    String mDescription;
    SHARE_MEDIA[] mPlatforms;
    SimpleShareListener mSimpleShareListener;
    UMShareListener mCustomShareListener;
    ShareBoardlistener mCustomShareBoardListener;
    List<String> mCustomBtnNames;
    List<String> mCustomBtnIcons;
    List<CustomBtnListener> mCustomBtnListeners;
    String mCopyUrl;
    String mCopyIcon;

    /**
     * 添加缩略图
     */
    public T setThumb(String thumbUrl) {
        mThumbUrl = thumbUrl;
        return (T) this;
    }

    public T setThumb(int thumbResource) {
        mThumbResource = thumbResource;
        return (T) this;
    }

    public T setThumb(Bitmap thumbBitmap) {
        mThumbBitmap = thumbBitmap;
        return (T) this;
    }

    public T setThumb(File thumbFile) {
        mThumbFile = thumbFile;
        return (T) this;
    }

    public T setThumb(byte[] thumbBytes) {
        mThumbBytes = thumbBytes;
        return (T) this;
    }

    /**
     * 添加标题
     */
    public T setTitle(String title) {
        mTitle = title;
        return (T) this;
    }

    /**
     * 添加描述
     */
    public T setDescription(String description) {
        mDescription = description;
        return (T) this;
    }

    /**
     * 分享包括文字（微博\QQ空间图文分享）
     */
    public T withText(String withText) {
        mWithText = withText;
        return (T) this;
    }

    /**
     * 设置分享面板包含的平台
     */
    public T setBoardDisplayList(SHARE_MEDIA... platforms) {
        mPlatforms = platforms;
        return (T) this;
    }

    /**
     * 设置简单的分享结果监听
     */
    public T setSimpleShareListener(SimpleShareListener simpleShareListener) {
        mSimpleShareListener = simpleShareListener;
        return (T) this;
    }

    /**
     * 设置自定义的分享结果监听
     */
    public T setCustomShareListener(UMShareListener customShareListener) {
        mCustomShareListener = customShareListener;
        return (T) this;
    }

    /**
     * 添加自定义分享面板按钮
     * 第一个参数是按钮名称，比如"复制链接"
     * 第二个是icon在drawable文件中的名字，比如umeng_socialize_copyurl
     * 第三个是自定义点击监听事件处理
     */
    public T addCustomBoardBtn(String btnText, String icon_text_from_drawable_xml, CustomBtnListener listener) {
        if (mCustomBtnNames == null) {
            mCustomBtnNames = new ArrayList<>();
        }
        if (mCustomBtnIcons == null) {
            mCustomBtnIcons = new ArrayList<>();
        }
        if (mCustomBtnListeners == null) {
            mCustomBtnListeners = new ArrayList<>();
        }
        mCustomBtnNames.add(btnText);
        mCustomBtnIcons.add(icon_text_from_drawable_xml);
        mCustomBtnListeners.add(listener);
        return (T) this;
    }

    /**
     * 在分享面板中显示复制链接按钮，按钮的icon可以在config中全局更改
     */
    public T showCopyUrlBtn(String copyUrl) {
        mCopyUrl = copyUrl;
        return (T) this;
    }

    /**
     * 在分享面板中显示复制链接按钮，同时设置icon
     * 注意此icon使用的是icon在drawable文件中的名字，比如umeng_socialize_copyurl
     */
    public T showCopyUrlBtn(String copyUrl, String copy_icon_text_from_drawable_xml) {
        mCopyUrl = copyUrl;
        mCopyIcon = copy_icon_text_from_drawable_xml;
        return (T) this;
    }

    /**
     * 以显示在中部的分享面板来分享
     */
    public void shareWithCenterBoard(Activity activity) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setIndicatorVisibility(false);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        config.setTitleTextColor(Color.BLACK);
        shareWithCustomBoard(activity, config);
    }

    /**
     * 以显示在底部的分享面板来分享
     */
    public void shareWithBottomBoard(Activity activity) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setCancelButtonVisibility(false);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        config.setMenuItemTextColor(Color.BLACK);
        shareWithCustomBoard(activity, config);
    }

    /**
     * 自定义分享面板分享
     */
    public void shareWithCustomBoard(final Activity activity, ShareBoardConfig config) {
        final ShareAction shareAction = new ShareAction(activity);
        shareAction.setDisplayList(getPlatforms());
        //如果设置了复制链接，则添加复制链接的按钮
        if (mCopyUrl != null) {
            //检查是否在全局config中设置了复制链接的icon，没有则使用默认icon
            String icon;
            if (mCopyIcon != null) {
                icon = mCopyIcon;
            } else {
                icon = UShareUtils.getConfig().getCopyUrlBtnIcon() != null ?
                        UShareUtils.getConfig().getCopyUrlBtnIcon() : "umeng_socialize_copyurl";
            }
            shareAction.addButton(BTN_COPY_URL_SHOW_WORD, BTN_COPY_URL_KEY_WORD, icon, icon);
        }
        if (mCustomBtnNames != null) {
            for (int i = 0; i < mCustomBtnNames.size(); i++) {
                String name = mCustomBtnNames.get(i);
                String icon = mCustomBtnIcons.get(i);
                shareAction.addButton(name, name, icon, icon);
            }
        }
        shareAction.setShareboardclickCallback(getBoardListener(activity, shareAction));
        shareAction.open(config);
    }

    /**
     * 直接分享到某平台
     */
    public void share(Activity activity, SHARE_MEDIA platform) {
        share(activity, platform, new ShareAction(activity));
    }

    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        shareAction.setPlatform(platform)
                .setCallback(getListener(activity))
                .share();
    }

    /**
     * 获取分享面板显示的分享平台
     */
    private SHARE_MEDIA[] getPlatforms() {
        //如果有设置面板分享平台则返回
        if (mPlatforms != null) {
            return mPlatforms;
        }
        //否则返回全局设置的参数
        if (UShareUtils.getConfig().getPlatforms() != null) {
            return UShareUtils.getConfig().getPlatforms();
        }
        //以上都没有则返回默认分享平台
        return new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE};
    }

    /**
     * 获取分享监听
     */
    private UMShareListener getListener(Activity activity) {
        //设置完整的自定义监听 优先级最高
        if (mCustomShareListener != null) {
            return mCustomShareListener;
        }
        //设置简单分享监听 只有完成和失败 优先级中
        if (mSimpleShareListener != null) {
            return new DefaultShareListener(activity, mSimpleShareListener);
        }
        //根据全局配置设置监听 优先级最低
        if (UShareUtils.getConfig().getShareListener() != null) {
            return UShareUtils.getConfig().getShareListener();
        }
        //如果以上都没有 则返回默认的监听器
        return new DefaultShareListener(activity);
    }

    /**
     * 获取分享面板按钮监听
     */
    private ShareBoardlistener getBoardListener(final Activity activity, final ShareAction shareAction) {
        //设置完整的自定义监听 优先级最高
        if (mCustomShareBoardListener != null) {
            return mCustomShareBoardListener;
        }
        return new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
                if (platform == null) {
                    //根据key来区分自定义按钮的类型，并进行对应的操作
                    if (mCopyUrl != null && snsPlatform.mKeyword.equals(BTN_COPY_URL_KEY_WORD)) {
                        //复制链接btn在传入url后，添加监听，点击执行复制操作
                        ClipboardManager manager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                        manager.setPrimaryClip(ClipData.newRawUri("Label", Uri.parse(mCopyUrl)));
                        Toast.makeText(activity, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                    }
                    //设置了自定义按钮时
                    if (mCustomBtnNames != null) {
                        for (int i = 0; i < mCustomBtnNames.size(); i++) {
                            if (snsPlatform.mKeyword.equals(mCustomBtnNames.get(i))) {
                                //为自定义按钮添加监听
                                mCustomBtnListeners.get(i).onBtnClick(snsPlatform);
                            }
                        }
                    }
                } else {
                    //社交平台的分享行为
                    share(activity, platform, shareAction);
                }
            }
        };
    }

    /**
     * 为Media添加缩略图、标题和描述
     */
    <M extends BaseMediaObject> M getMedia(M m, Context context) {
        if (UShareUtils.getConfig().getThumb() != null) {
            m.setThumb(UShareUtils.getConfig().getThumb());
        }
        if (UShareUtils.getConfig().getTitle() != null) {
            m.setTitle(UShareUtils.getConfig().getTitle());
        }
        if (UShareUtils.getConfig().getDescription() != null) {
            m.setDescription(UShareUtils.getConfig().getDescription());
        }
        if (mThumbFile != null) {
            m.setThumb(new UMImage(context, mThumbFile));
        }
        if (mThumbBytes != null) {
            m.setThumb(new UMImage(context, mThumbBytes));
        }
        if (mThumbBitmap != null) {
            m.setThumb(new UMImage(context, mThumbBitmap));
        }
        if (mThumbUrl != null) {
            m.setThumb(new UMImage(context, mThumbUrl));
        }
        if (mThumbResource != 0) {
            m.setThumb(new UMImage(context, mThumbResource));
        }
        if (mTitle != null) {
            m.setTitle(mTitle);
        }
        if (mDescription != null) {
            m.setDescription(mDescription);
        }
        return m;
    }

}
