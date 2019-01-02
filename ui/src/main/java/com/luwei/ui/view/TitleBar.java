package com.luwei.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luwei.ui.R;


/**
 * Created by LiCheng
 * Date：2018/12/10
 */
public class TitleBar extends RelativeLayout {

    private Context mContext;
    private static Config mConfig = new Config();

    private static final int w = LayoutParams.WRAP_CONTENT;
    private static final int h = LayoutParams.MATCH_PARENT;

    private static int mDefPadding;
    private static int mDefTextSize;
    private static int mDefTextColor;
    private static int mDefBackGroundColor;
    private static Drawable mDefLeftImage;

    //是否添加默认的返回键
    private boolean mIsBack = true;

    private int mPadding;
    private int mTitleTextColor;
    private int mRightTextColor;
    private int mTitleTextSize;
    private int mRightTextSize;

    private String mTitleText;
    private String mRightText;

    private Drawable mLeftImage;
    private Drawable mRightImage;

    private TextView mTvTitle;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private ImageView mIvRight;


    private OnLeftClickListener mLeftListener;
    private OnRightClickListener mRightListener;
    private OnTitleClickListener mTitleListener;

    public TitleBar(@NonNull Context context) {
        this(context, null);

    }

    public TitleBar(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initDefaultParam();
        getAttr(attrs);
        initTitle();
        initLeft();
        initRight();
    }

    private void initDefaultParam() {
        if (mDefPadding == 0) {
            mDefPadding = dip2px(mContext, 14.0F);
        }
        if (mDefTextSize == 0) {
            mDefTextSize = 15;
        }
        if (mDefTextColor == 0) {
            mDefTextColor = Color.parseColor("#333333");
        }
        if (mDefBackGroundColor == 0) {
            mDefBackGroundColor = Color.parseColor("#3F51B5");
        }
        if (mIsBack && mDefLeftImage == null) {
            mDefLeftImage = ContextCompat.getDrawable(mContext,R.mipmap.top_return);
        }
        setBackgroundColor(mDefBackGroundColor);
    }

    private void getAttr(AttributeSet attrs) {
        //从xml中获取属性
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mIsBack = ta.getBoolean(R.styleable.TitleBar_isBack, true);
        mPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_padding, mDefPadding);
        mTitleTextColor = ta.getColor(R.styleable.TitleBar_titleTextColor, mDefTextColor);
        mTitleText = ta.getString(R.styleable.TitleBar_titleText);
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_titleTextSize, mDefTextSize);
        mLeftImage = ta.getDrawable(R.styleable.TitleBar_leftImage);
        mRightText = ta.getString(R.styleable.TitleBar_rightText);
        mRightTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, mDefTextSize);
        mRightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor, mDefTextColor);
        mRightImage = ta.getDrawable(R.styleable.TitleBar_rightImage);
        ta.recycle();
    }

    private void initTitle() {
        //未设置标题时 获取Label作为标题
        if (mTitleText == null) {
            try {
                //获取manifest中label，设置为标题
                String label = getActivityLabel(getActivity());
                if (label != null) {
                    mTitleText = label;
                } else {
                    mTitleText = "请设置标题或Label";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        mTvTitle = new TextView(mContext);
        mTvTitle.setSingleLine(true);
        mTvTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTvTitle.setText(mTitleText);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleListener != null) {
                    mTitleListener.titleClick();
                }
            }
        });


        LayoutParams titleParams = new LayoutParams(w, h);
        titleParams.addRule(CENTER_IN_PARENT);
        mTvTitle.setGravity(Gravity.CENTER_VERTICAL);

        addView(mTvTitle, titleParams);
    }

    private void initLeft() {
        LayoutParams leftParams = new LayoutParams(w, h);
        leftParams.addRule(ALIGN_PARENT_LEFT);
        mIvLeft = new ImageView(mContext);
        mIvLeft.setPadding(mPadding, 0, mPadding * 2, 0);
        mIvLeft.setLayoutParams(leftParams);
        mIvLeft.setScaleType(ImageView.ScaleType.FIT_XY);
        mIvLeft.setAdjustViewBounds(true);
        //没有设置图片资源时设置默认的返回图片
        if (mDefLeftImage != null && mLeftImage == null) {
            mLeftImage = mDefLeftImage;
        }

        if (mLeftImage != null) {
            mIvLeft.setImageDrawable(mLeftImage);
            mIvLeft.setVisibility(VISIBLE);
        } else {
            mIvLeft.setVisibility(GONE);
        }

        mIvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLeftListener != null) {
                    mLeftListener.leftClick();
                } else {
                    try {
                        Activity activity = getActivity();
                        closeKeyboard(activity);
                        activity.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.addView(mIvLeft);

    }

    private void initRight() {
        LayoutParams rightParams = new LayoutParams(w, h);
        rightParams.addRule(ALIGN_PARENT_RIGHT);

        mTvRight = new TextView(mContext);
        mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightTextSize);
        mTvRight.setHeight(h);
        mTvRight.setGravity(Gravity.CENTER);
        mTvRight.setSingleLine(true);
        mTvRight.setEllipsize(TextUtils.TruncateAt.END);
        mTvRight.setPadding(mPadding, 0, mPadding, 0);
        mTvRight.setLayoutParams(rightParams);
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRightListener != null) {
                    mRightListener.rightClick();
                }
            }
        });
        if (mRightText != null) {
            mTvRight.setText(mRightText);
            mTvRight.setVisibility(VISIBLE);
        } else {
            mTvRight.setVisibility(GONE);
        }
        this.addView(mTvRight);

        mIvRight = new ImageView(mContext);
        mIvRight.setPadding(mPadding, 0, mPadding, 0);
        mIvRight.setLayoutParams(rightParams);
        mIvRight.setScaleType(ImageView.ScaleType.FIT_XY);
        mIvRight.setAdjustViewBounds(true);
        mIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRightListener != null) {
                    mRightListener.rightClick();
                }
            }
        });
        if (mRightImage != null) {
            mIvRight.setImageDrawable(mRightImage);
            mIvRight.setVisibility(VISIBLE);
        } else {
            mIvRight.setVisibility(GONE);
        }
        this.addView(mIvRight);

    }


    /**
     * 设置左部图片
     */
    public void setLeftImage(Drawable drawable) {
        if (drawable == null) {
            mIvLeft.setVisibility(GONE);
            return;
        }
        mIvLeft.setVisibility(VISIBLE);
        this.mLeftImage = drawable;
        mIvLeft.setImageDrawable(mLeftImage);
    }

    public void setLeftImage(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        this.setLeftImage(drawable);
    }

    /**
     * 设置右部图片
     */
    public void setRightImage(Drawable drawable) {
        if (drawable == null) {
            mIvRight.setVisibility(GONE);
            return;
        }
        mIvRight.setVisibility(VISIBLE);
        this.mRightImage = drawable;
        mIvRight.setImageDrawable(mRightImage);
    }

    public void setRightImage(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        this.setRightImage(drawable);
    }

    /**
     * 设置标题文字
     */
    public void setTitleText(String title) {
        this.mTitleText = title;
        mTvTitle.setText(title);
    }

    /**
     * 设置标题文字
     */
    public void setTitleText(@StringRes int resId) {
        this.mTitleText = mContext.getResources().getText(resId).toString();
        mTvTitle.setText(mTitleText);
    }

    /**
     * 设置标题文字颜色
     */
    public void setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        mTvTitle.setTextColor(mTitleTextColor);
    }

    /**
     * 设置标题文字大小
     */
    public void setTitleTextSize(@Dimension int sp) {
        this.mTitleTextSize = sp;
        mTvTitle.setTextSize(mTitleTextSize);
    }


    /**
     * 设置菜单文字
     */
    public void setRightText(String rightText) {
        if (TextUtils.isEmpty(rightText)) {
            mTvRight.setVisibility(GONE);
            return;
        }
        this.mRightText = rightText;
        mTvRight.setText(rightText);
        mTvRight.setVisibility(VISIBLE);
    }

    /**
     * 设置菜单文字
     */
    public void setRightText(@StringRes int resId) {
        if (resId == 0) {
            mTvRight.setVisibility(GONE);
            return;
        }
        this.mRightText = mContext.getResources().getText(resId).toString();
        mTvRight.setText(mRightText);
        mTvRight.setVisibility(VISIBLE);
    }

    /**
     * 设置菜单文字颜色
     */
    public void setRightTextColor(@ColorInt int color) {
        this.mRightTextColor = color;
        mTvRight.setTextColor(mRightTextColor);
    }

    /**
     * 设置菜单文字大小
     */
    public void setRightTextSize(@Dimension int sp) {
        this.mRightTextSize = sp;
        mTvRight.setTextSize(mRightTextSize);
    }

    /**
     * 设置背景颜色
     *
     * @param color color
     */
    public void setBackGroundColor(@ColorInt int color) {
        int mBackGroundColor = color;
        this.setBackgroundColor(mBackGroundColor);

    }

    /**
     * 左部点击监听
     *
     * @param listener
     */
    public void setLeftClickListener(OnLeftClickListener listener) {
        this.mLeftListener = listener;
    }

    /**
     * 右部点击监听
     *
     * @param listener
     */
    public void setRightClickListener(OnRightClickListener listener) {
        this.mRightListener = listener;
    }

    /**
     * 标题点击监听
     *
     * @param listener
     */
    public void setTitleClickListener(OnTitleClickListener listener) {
        this.mTitleListener = listener;
    }


    public static Config getConfig() {
        return mConfig;
    }


    /**
     * 获取activity
     *
     * @return
     * @throws Exception Unable to get Activity.
     */
    private Activity getActivity() throws Exception {
        Context context = getContext();
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new Exception("Unable to get Activity.");
    }

    /**
     * @param activity 获取 Activity 的Label属性值
     * @return
     */
    static String getActivityLabel(Activity activity) {
        //获取清单文件中的label属性值
        CharSequence label = activity.getTitle();
        //如果Activity没有设置label属性，则默认会返回APP名称，需要过滤掉
        if (label != null && !label.toString().equals("")) {

            try {
                PackageManager packageManager = activity.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);

                if (!label.toString().equals(packageInfo.applicationInfo.loadLabel(packageManager).toString())) {
                    return label.toString();
                }
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return null;
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    private void closeKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }

        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public interface OnLeftClickListener {
        void leftClick();
    }

    public interface OnRightClickListener {
        void rightClick();
    }

    public interface OnTitleClickListener {
        void titleClick();
    }


    /**
     * 全局配置默认参数
     */
    public static class Config {

        public Config setPadding(Context context, @Dimension int dp) {
            mDefPadding = dip2px(context, dp);
            return mConfig;
        }

        public Config setTitleTextSize(Context context, @Dimension int sp) {
            mDefTextSize = sp;
            return mConfig;
        }

        public Config setTitleTextColor(@ColorInt int color) {
            mDefTextColor = color;
            return mConfig;
        }

        public Config setBackGroundColor(@ColorInt int color) {
            mDefBackGroundColor = color;
            return mConfig;
        }

        public Config setLeftImage(Drawable leftImage) {
            mDefLeftImage = leftImage;
            return mConfig;
        }
    }

}
