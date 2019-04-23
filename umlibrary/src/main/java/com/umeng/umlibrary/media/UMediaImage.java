package com.umeng.umlibrary.media;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;


/**
 * @author LiCheng
 * @date 2019/3/2
 */
public class UMediaImage extends UMediaBase<UMediaImage> {

    private String url;
    private int resource;
    private Bitmap bitmap;
    private File file;
    private byte[] bytes;
    private Bitmap.CompressFormat compressFormat;
    private UMImage.CompressStyle compressStyle;

    /**
     * 添加网络图片
     * 分享的图片需要设置缩略图
     */
    public UMediaImage(String url) {
        this.url = url;
    }

    /**
     * 添加资源文件
     */
    public UMediaImage(int resource) {
        this.resource = resource;
    }

    public UMediaImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public UMediaImage(File file) {
        this.file = file;
    }

    public UMediaImage(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 设置图片格式
     *
     * @param format
     * @return
     */
    public UMediaImage setCompressFormat(Bitmap.CompressFormat format) {
        this.compressFormat = format;
        return this;
    }

    /**
     * 设置图片压缩 用户设置的图片大小最好不要超过250k，缩略图不要超过18k
     * 如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩
     * UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
     * UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享压缩格式设置
     *
     * @param style
     * @return
     */
    public UMediaImage setCompressStyle(UMImage.CompressStyle style) {
        this.compressStyle = style;
        return this;
    }

    private UMImage createUmImage(Context context) {
        if (url != null) {
            return new UMImage(context, url);
        }
        if (resource != 0) {
            return new UMImage(context, resource);
        }
        if (bitmap != null) {
            return new UMImage(context, bitmap);
        }
        if (file != null) {
            return new UMImage(context, file);
        }
        if (bytes != null) {
            return new UMImage(context, bytes);
        }
        return null;
    }

    private UMImage getUmImage(Context context) {
        UMImage umImage = createUmImage(context);
        if (compressFormat != null) {
            umImage.compressFormat = compressFormat;
        }
        if (compressStyle != null) {
            umImage.compressStyle = compressStyle;
        }
        return umImage;
    }

    @Override
    protected void share(Activity activity, SHARE_MEDIA platform, ShareAction shareAction) {
        if (mWithText != null) {
            shareAction.withText(mWithText);
        }
        shareAction.withMedia(getMedia(getUmImage(activity), activity));
        super.share(activity, platform, shareAction);
    }

    @Override
    public UMediaImage setTitle(String title) {
        return super.setTitle(title);
    }

    @Override
    public UMediaImage setDescription(String description) {
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
