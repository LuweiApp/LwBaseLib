package com.luwei.lwbaselib.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.base.IPresent;
import com.luwei.base.LwBaseActivity;
import com.luwei.lwbaselib.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.umlibrary.UAuthUtils;
import com.umeng.umlibrary.UShareUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LiCheng
 */
public class UMShareAndAuthActivity extends LwBaseActivity {
    @BindView(R.id.sp_platform)
    Spinner mSpPlatform;
    @BindView(R.id.sp_type)
    Spinner mSpType;
    @BindView(R.id.tv_authorize)
    TextView mTvAuthorize;

    private String mThumb;
    private String mTitle;
    private String mDescription;
    private String mText;
    private String mWeb;
    private String mImage;
    private String mVideo;
    private String mMusic;
    private String mMusicTarget;
    private String mGif;
    private String mMin;

    private static SHARE_MEDIA[] sharePlatforms = new SHARE_MEDIA[]{
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.WEIXIN_FAVORITE,
            SHARE_MEDIA.SINA,
            SHARE_MEDIA.QQ,
            SHARE_MEDIA.QZONE
    };

    private int mShareType;

    private SHARE_MEDIA mSharePlatform;
    private ShareBoardConfig mConfig;


    /**
     * 请完成初始化，在Application的onCreate中调用 UMConfigure.init(this,appkey,channel,UMConfigure.DEVICE_TYPE_PHONE,"")
     * 添加各平台的APPKEY等信息
     * 并按照https://juejin.im/post/5c09e118f265da61483b6939 中内容完善Manifest以及添加权限
     * 最后记得在onActivityResult()中添加回调 UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
     * 和在onDestroy()中添加 UMShareAPI.get(this).release() 防止内存泄漏
     * <p>
     * 本DEMO中，因为lwbase库并未在各开放平台中申请，所以并不能分享出去以及获得授权，以下只作为代码示例展示用。
     */
    @Override
    public void initView(Bundle savedInstanceState) {
        initShareData();
        initSpinner();
        initCustomBoard();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //记得添加回调
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        UMShareAPI.get(this).release();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_um_share_and_auth;
    }

    @Override
    public IPresent newP() {
        return null;
    }

    @OnClick({R.id.btn_share, R.id.btn_authorize, R.id.btn_board_bottom, R.id.btn_board_center,
            R.id.btn_board_customize, R.id.btn_authorize_wechat, R.id.btn_delete_auth,
            R.id.btn_show_info, R.id.btn_is_wechat_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                share();
                break;
            case R.id.btn_authorize:
                authorize();
                break;
            case R.id.btn_board_bottom:
                shareWithBottomBoard();
                break;
            case R.id.btn_board_center:
                shareWithCenterBoard();
                break;
            case R.id.btn_board_customize:
                shareWithCustomBoard();
                break;
            case R.id.btn_authorize_wechat:
                UAuthUtils.authorizeWeChat(this);
                break;
            case R.id.btn_delete_auth:
                UAuthUtils.deleteOauth(this);
                break;
            case R.id.btn_is_wechat_auth:
                ToastIsWeChatAuthorize();
                break;
            case R.id.btn_show_info:
                showInfo(UAuthUtils.getWeChatInfoMap());
                break;
            default:
        }
    }

    private void initShareData() {
        mTitle = "分享测试标题";
        mThumb = "http://img5.mtime.cn/mg/2019/02/18/094450.82568353_120X90X4.jpg";
        mText = "分享测试文本";
        mWeb = "https://gank.io/";
        mDescription = "分享测试描述";
        mImage = "https://user-gold-cdn.xitu.io/2019/2/28/169334caeb7892ed?w=2069&h=1164&f=jpeg&s=1380223.jpg";
        mVideo = "http://vfx.mtime.cn/Video/2019/02/18/mp4/190218094554764087.mp4";
        mMusic = "http://www.170hi.com/kw/other.web.nf01.sycdn.kuwo.cn/resource/n3/41/97/3247003227.mp3";
        mMusicTarget = "https://y.qq.com/n/yqq/mv/v/o002752g33w.html#stat=y_new.mv.othercmv.play";
        mGif = "https://static.dongtu.com/watermake/20190107140151OC67XLK5B0MSNHFF_m_mc.gif";
        mMin = "http://img5.mtime.cn/mg/2019/02/18/094450.82568353_120X90X4.jpg";
    }

    private void initSpinner() {
        mSpPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSharePlatform = sharePlatforms[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mShareType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initCustomBoard() {
        mConfig = new ShareBoardConfig();
        mConfig.setCancelButtonVisibility(false);
        mConfig.setTitleVisibility(true);
        mConfig.setIndicatorVisibility(false);
        mConfig.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        mConfig.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE, 8);
        mConfig.setShareboardBackgroundColor(Color.parseColor("#f5f5f5"));
        mConfig.setMenuItemTextColor(Color.BLACK);
        mConfig.setTitleTextColor(Color.BLACK);
    }

    private void share() {
        switch (mShareType) {
            case 0:
                UShareUtils.with(this)
                        .createText(mText)
                        .share(mSharePlatform);
                break;
            case 1:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .share(mSharePlatform);
                break;
            case 2:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .withText(mText)
                        .share(mSharePlatform);
                break;
            case 3:
                UShareUtils.with(this)
                        .createWeb(mWeb)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .share(mSharePlatform);
                break;
            case 4:
                UShareUtils.with(this)
                        .createVideo(mVideo)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .share(mSharePlatform);
                break;
            case 5:
                UShareUtils.with(this)
                        .createMusic(mMusic)
                        .setTargetUrl(mMusicTarget)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .share(mSharePlatform);
                break;
            case 6:
                UShareUtils.with(this)
                        .createGif(mGif)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .share(mSharePlatform);
                break;
            case 7:
                UShareUtils.with(this)
                        .createMinApp(mMin)
                        .setPath("pages/index/index")
                        .setUserName("gh_9e4dcf49350c")
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .share(mSharePlatform);
                break;
            default:
        }
    }

    private void shareWithCenterBoard() {
        switch (mShareType) {
            case 0:
                UShareUtils.with(this)
                        .createText(mText)
                        .shareWithCenterBoard();
                break;
            case 1:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .shareWithCenterBoard();
                break;
            case 2:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .withText(mText)
                        .shareWithCenterBoard();
                break;
            case 3:
                UShareUtils.with(this)
                        .createWeb(mWeb)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCenterBoard();
                break;
            case 4:
                UShareUtils.with(this)
                        .createVideo(mVideo)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCenterBoard();
                break;
            case 5:
                UShareUtils.with(this)
                        .createMusic(mMusic)
                        .setTargetUrl(mMusicTarget)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCenterBoard();
                break;
            case 6:
                UShareUtils.with(this)
                        .createGif(mGif)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCenterBoard();
                break;
            case 7:
                UShareUtils.with(this)
                        .createMinApp(mMin)
                        .setPath("pages/index/index")
                        .setUserName("gh_9e4dcf49350c")
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCenterBoard();
                break;
            default:
        }
    }

    private void shareWithBottomBoard() {
        switch (mShareType) {
            case 0:
                UShareUtils.with(this)
                        .createText(mText)
                        .shareWithBottomBoard();
                break;
            case 1:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .shareWithBottomBoard();
                break;
            case 2:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .withText(mText)
                        .shareWithBottomBoard();
                break;
            case 3:
                UShareUtils.with(this)
                        .createWeb(mWeb)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithBottomBoard();
                break;
            case 4:
                UShareUtils.with(this)
                        .createVideo(mVideo)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithBottomBoard();
                break;
            case 5:
                UShareUtils.with(this)
                        .createMusic(mMusic)
                        .setTargetUrl(mMusicTarget)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithBottomBoard();
                break;
            case 6:
                UShareUtils.with(this)
                        .createGif(mGif)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithBottomBoard();
                break;
            case 7:
                UShareUtils.with(this)
                        .createMinApp(mMin)
                        .setPath("pages/index/index")
                        .setUserName("gh_9e4dcf49350c")
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithBottomBoard();
                break;
            default:
        }
    }

    private void shareWithCustomBoard() {
        switch (mShareType) {
            case 0:
                UShareUtils.with(this)
                        .createText(mText)
                        .shareWithCustomBoard(mConfig);
                break;
            case 1:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .shareWithCustomBoard(mConfig);
                break;
            case 2:
                UShareUtils.with(this)
                        .createImage(mImage)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .setCompressStyle(UMImage.CompressStyle.SCALE)
                        .withText(mText)
                        .shareWithCustomBoard(mConfig);
                break;
            case 3:
                UShareUtils.with(this)
                        .createWeb(mWeb)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCustomBoard(mConfig);
                break;
            case 4:
                UShareUtils.with(this)
                        .createVideo(mVideo)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCustomBoard(mConfig);
                break;
            case 5:
                UShareUtils.with(this)
                        .createMusic(mMusic)
                        .setTargetUrl(mMusicTarget)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCustomBoard(mConfig);
                break;
            case 6:
                UShareUtils.with(this)
                        .createGif(mGif)
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCustomBoard(mConfig);
                break;
            case 7:
                UShareUtils.with(this)
                        .createMinApp(mMin)
                        .setPath("pages/index/index")
                        .setUserName("gh_9e4dcf49350c")
                        .setThumb(mThumb)
                        .setTitle(mTitle)
                        .setDescription(mDescription)
                        .shareWithCustomBoard(mConfig);
                break;
            default:
        }
    }

    private void authorize() {
        UMShareAPI.get(this).getPlatformInfo(this, mSharePlatform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");
                showInfo(map);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    private void showInfo(Map<String, String> infoMap) {
        String mapString = infoMap.toString();
        String mapText = mapString.substring(1, mapString.length() - 1).replace(", ", "\n\n");
        mTvAuthorize.setGravity(Gravity.NO_GRAVITY);
        mTvAuthorize.setTextColor(Color.BLACK);
        mTvAuthorize.setText(mapText);
    }

    private void ToastIsWeChatAuthorize() {
        boolean isWeChatAuthorize = UAuthUtils.isWeChatAuthorize(this);
        if (isWeChatAuthorize) {
            Toast.makeText(this, "微信已授权", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "微信未授权", Toast.LENGTH_SHORT).show();
        }

    }

}
