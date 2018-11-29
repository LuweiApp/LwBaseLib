package com.luwei.lwbaselib.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.luwei.lwbaselib.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.luwei.mvp.imageloader.ImageLoaderUtils;
/**
 * 显示图片框架使用的 activity
 */
public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_normal)
    ImageView mIvNormal;
    @BindView(R.id.iv_circle)
    ImageView mIvCircle;
    @BindView(R.id.iv_from_drawable)
    ImageView mIvFromDrawable;
    @BindView(R.id.iv_round)
    ImageView mIvRound;
    @BindView(R.id.iv_gif)
    ImageView mIvGif;
    @BindView(R.id.iv_blur)
    ImageView mIvBlur;
    @BindView(R.id.iv_blur1)
    ImageView mIvBlur1;
    @BindView(R.id.iv_blur_befor)
    ImageView mIvBlurBefor;
    @BindView(R.id.iv_mask)
    ImageView mIvMask;

    //    private String urlPath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540821255682&di=0fc90d9ffd5a8b5f0588369f7c17c607&imgtype=0&src=http%3A%2F%2Fpic32.nipic.com%2F20130827%2F2531170_141548474000_2.jpg";
    private String urlPath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540878104990&di=43ab375e5c37598078e9e55ca8e35288&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-10-12%2F59df06a898fbb.jpg";
    private String gitUrlPath = "http://b.hiphotos.baidu.com/image/pic/item/ac345982b2b7d0a2a5f98d86c0ef76094b369afd.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setTitle("图片框架使用示例");

        ImageLoaderUtils.loadImage(this, mIvNormal, urlPath);
        ImageLoaderUtils.loadCircleImage(this, mIvCircle, urlPath);
        ImageLoaderUtils.loadRoundedImage(this, mIvRound, urlPath, 20);
        ImageLoaderUtils.loadGifImage(this, mIvGif, gitUrlPath);
        ImageLoaderUtils.loadImageFromDrawable(this, mIvFromDrawable, R.mipmap.sunyizhen);

        ImageLoaderUtils.loadMarkImage(this, mIvMask, urlPath,R.mipmap.mask_starfish);
        ImageLoaderUtils.loadImage(this, mIvBlurBefor, urlPath);
        ImageLoaderUtils.loadBlurImage(this, mIvBlur, urlPath, 10);
        ImageLoaderUtils.loadBlurImage(this, mIvBlur1, urlPath, 25);


//        Glide.with(this)
//                .load(urlPath)
//                .apply(RequestOptions.bitmapTransform(new GlideBlurformation(this,10)))
//                .into(mIvBlur);

    }
}
