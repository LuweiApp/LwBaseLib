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
 * @author LiCheng
 * @date 2018/12/10
 */
public class TitleBar extends RelativeLayout {

    private Context mContext;
    private static Config mConfig = new Config();

    private static final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
    private static final int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    private static int mDefHeight;
    private static int mDefPadding;
    private static int mDefDrawablePadding;
    private static int mDefBackGroundColor;

    private static float mDefTitleSize;
    private static float mDefLeftSize;
    private static float mDefRightSize;

    private static int mDefTitleColor;
    private static int mDefLeftColor;
    private static int mDefRightColor;

    private static Drawable mDefLeftImage;
    private boolean mShowDefaultBackIcon = true;

    private int mPadding;
    private int mLeftDrawablePadding;
    private int mRightDrawablePadding;
    private int mTitleTextColor;
    private int mLeftTextColor;
    private int mRightTextColor;

    private int mTitleTextSize;
    private int mLeftTextSize;
    private int mRightTextSize;

    private String mTitleText;
    private String mLeftText;
    private String mRightText;

    private Drawable mLeftImage;
    private Drawable mLeftDrawableLeft;
    private Drawable mLeftDrawableRight;
    private Drawable mRightImage;
    private Drawable mRightDrawableLeft;
    private Drawable mRightDrawableRight;

    private TextView mTvTitle;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private ImageView mIvRight;

    private LayoutParams mTitleParams;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;

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
        initTitleBar();
    }

    private void initDefaultParam() {
        if (mDefHeight == 0) {
            mDefHeight = dp2px(mContext, 42.0F);
        }
        if (mDefPadding == 0) {
            mDefPadding = dp2px(mContext, 14.0F);
        }
        if (mDefDrawablePadding == 0) {
            mDefDrawablePadding = dp2px(mContext, 6.0F);
        }
        if (mDefTitleSize == 0) {
            mDefTitleSize = sp2px(mContext, 15.0F);
        }
        if (mDefTitleColor == 0) {
            mDefTitleColor = Color.parseColor("#262122");
        }
        if (mDefLeftSize == 0) {
            mDefLeftSize = sp2px(mContext, 15.0F);
        }
        if (mDefLeftColor == 0) {
            mDefLeftColor = Color.parseColor("#262122");
        }
        if (mDefRightSize == 0) {
            mDefRightSize = sp2px(mContext, 15.0F);
        }
        if (mDefRightColor == 0) {
            mDefRightColor = Color.parseColor("#262122");
        }
        if (mDefBackGroundColor == 0) {
            mDefBackGroundColor = Color.parseColor("#ffffff");
        }
        if (mDefLeftImage == null) {
            mDefLeftImage = ContextCompat.getDrawable(mContext, R.mipmap.top_return);
        }
    }

    private void getAttr(AttributeSet attrs) {
        //从xml中获取属性
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        mShowDefaultBackIcon = ta.getBoolean(R.styleable.TitleBar_showDefaultBackIcon, true);
        mPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_padding, mDefPadding);

        mTitleTextColor = ta.getColor(R.styleable.TitleBar_titleTextColor, mDefTitleColor);
        mTitleText = ta.getString(R.styleable.TitleBar_titleText);
        mTitleTextSize = px2sp(mContext, ta.getDimension(R.styleable.TitleBar_titleTextSize, mDefTitleSize));

        mLeftText = ta.getString(R.styleable.TitleBar_leftText);
        mLeftTextSize = px2sp(mContext, ta.getDimension(R.styleable.TitleBar_leftTextSize, mDefLeftSize));
        mLeftTextColor = ta.getColor(R.styleable.TitleBar_leftTextColor, mDefLeftColor);
        mLeftDrawableLeft = ta.getDrawable(R.styleable.TitleBar_leftTextDrawableLeft);
        mLeftDrawableRight = ta.getDrawable(R.styleable.TitleBar_leftTextDrawableRight);
        mLeftDrawablePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_leftTextDrawablePadding, mDefDrawablePadding);
        mLeftImage = ta.getDrawable(R.styleable.TitleBar_leftImage);

        mRightText = ta.getString(R.styleable.TitleBar_rightText);
        mRightTextSize = px2sp(mContext, ta.getDimension(R.styleable.TitleBar_rightTextSize, mDefRightSize));
        mRightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor, mDefRightColor);
        mRightDrawableLeft = ta.getDrawable(R.styleable.TitleBar_rightTextDrawableLeft);
        mRightDrawableRight = ta.getDrawable(R.styleable.TitleBar_rightTextDrawableRight);
        mRightDrawablePadding = ta.getDimensionPixelSize(R.styleable.TitleBar_rightTextDrawablePadding, mDefDrawablePadding);
        mRightImage = ta.getDrawable(R.styleable.TitleBar_rightImage);

        ta.recycle();
    }

    private void initTitleBar() {
        setTitleBar();
        initParams();
        setTitle();

        if (mLeftText == null) {
            setLeftImage();
        } else {
            setLeftText();
        }

        if (mRightText != null) {
            setRightText();
        } else if (mRightImage != null) {
            setRightImage();
        }

    }

    private void initParams() {
        mTitleParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        mTitleParams.addRule(CENTER_IN_PARENT);
        mLeftParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        mLeftParams.addRule(ALIGN_PARENT_LEFT);
        mRightParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        mRightParams.addRule(ALIGN_PARENT_RIGHT);
    }

    private void setTitleBar() {
        //判断是否在xml中设置背景颜色，没有则添加默认背景颜色
        if (getBackground() == null) {
            setBackgroundColor(mDefBackGroundColor);
        }
    }

    private void setTitle() {
        //未设置标题时 获取Label作为标题
        if (mTitleText == null) {
            try {
                //获取manifest中label，设置为标题
                String label = getActivityLabel(getActivity());
                if (label != null) {
                    mTitleText = label;
                } else {
                    mTitleText = "请设置标题或在Manifest中设置Label";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        mTvTitle = new TextView(mContext);
        mTvTitle.setSingleLine(true);
        mTvTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTvTitle.setText(mTitleText);
        mTvTitle.setTextSize(mTitleTextSize);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleListener != null) {
                    mTitleListener.titleClick();
                }
            }
        });

        mTvTitle.setGravity(Gravity.CENTER);
        addView(mTvTitle, mTitleParams);
    }

    private void setLeftImage() {
        //mShowDefaultBackIcon是选择是否添加默认返回键的布尔值，如果为false且用户未设置左部图片，则不显示
        if (!mShowDefaultBackIcon && mLeftImage == null) {
            return;
        }
        //没有设置图片资源时设置默认的返回图片
        if (mDefLeftImage != null && mLeftImage == null) {
            mLeftImage = mDefLeftImage;
        }

        mIvLeft = new ImageView(mContext);
        mIvLeft.setPadding(mPadding, 0, mPadding * 2, 0);
        mIvLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mIvLeft.setImageDrawable(mLeftImage);
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
        addView(mIvLeft, mLeftParams);
    }

    private void setLeftText() {
        mTvLeft = new TextView(mContext);
        mTvLeft.setText(mLeftText);
        mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftTextSize);
        mTvLeft.setTextColor(mLeftTextColor);
        mTvLeft.setHeight(MATCH_PARENT);
        mTvLeft.setGravity(Gravity.CENTER);
        mTvLeft.setSingleLine(true);
        mTvLeft.setEllipsize(TextUtils.TruncateAt.END);
        mTvLeft.setPadding(mPadding, 0, mPadding, 0);
        if (mLeftDrawableLeft != null) {
            mTvLeft.setCompoundDrawablesWithIntrinsicBounds(mLeftDrawableLeft, null, null, null);
            mTvLeft.setCompoundDrawablePadding(mLeftDrawablePadding);
        }
        if (mLeftDrawableRight != null) {
            mTvLeft.setCompoundDrawablesWithIntrinsicBounds(null, null, mLeftDrawableRight, null);
            mTvLeft.setCompoundDrawablePadding(mLeftDrawablePadding);
        }
        mTvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLeftListener != null) {
                    mLeftListener.leftClick();
                }
            }
        });
        addView(mTvLeft, mLeftParams);
    }

    private void setRightImage() {
        mIvRight = new ImageView(mContext);
        mIvRight.setPadding(mPadding, 0, mPadding, 0);
        mIvRight.setImageDrawable(mRightImage);
        mIvRight.setVisibility(VISIBLE);
        mIvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRightListener != null) {
                    mRightListener.rightClick();
                }
            }
        });
        addView(mIvRight, mRightParams);
    }

    private void setRightText() {
        mTvRight = new TextView(mContext);
        mTvRight.setText(mRightText);
        mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightTextSize);
        mTvRight.setTextColor(mRightTextColor);
        mTvRight.setHeight(MATCH_PARENT);
        mTvRight.setGravity(Gravity.CENTER);
        mTvRight.setSingleLine(true);
        mTvRight.setEllipsize(TextUtils.TruncateAt.END);
        mTvRight.setPadding(mPadding, 0, mPadding, 0);
        if (mRightDrawableLeft != null) {
            mTvRight.setCompoundDrawablesWithIntrinsicBounds(mRightDrawableLeft, null, null, null);
            mTvRight.setCompoundDrawablePadding(mRightDrawablePadding);
        }
        if (mRightDrawableRight != null) {
            mTvRight.setCompoundDrawablesWithIntrinsicBounds(null, null, mRightDrawableRight, null);
            mTvRight.setCompoundDrawablePadding(mRightDrawablePadding);
        }
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRightListener != null) {
                    mRightListener.rightClick();
                }
            }
        });
        addView(mTvRight, mRightParams);
    }


    /**
     * 如果高度为wrap_content 将高度设为默认的高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            getLayoutParams().height = mDefHeight;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
     * 设置左部图片
     */
    public void setLeftImage(Drawable drawable) {
        this.mLeftImage = drawable;
        if (mIvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! If you set the left text,The left ImageView won't be created.");

        } else {
            mIvLeft.setImageDrawable(mLeftImage);
        }
    }

    public void setLeftImage(@DrawableRes int resId) {
        this.setLeftImage(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * 设置左部文字
     */
    public void setLeftText(String leftText) {
        this.mLeftText = leftText;
        if (mTvLeft == null) {
            setLeftText();
            if (mIvLeft != null) {
                mIvLeft.setVisibility(GONE);
            }
        } else {
            mTvLeft.setText(mLeftText);
        }
    }

    /**
     * 设置左部文字
     */
    public void setLeftText(@StringRes int resId) {
        setLeftText(mContext.getResources().getText(resId).toString());
    }

    /**
     * 设置左部文字颜色
     */
    public void setLeftTextColor(@ColorInt int color) {
        this.mLeftTextColor = color;
        if (mTvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set left text first.");
        } else {
            mTvLeft.setTextColor(mLeftTextColor);
        }

    }

    /**
     * 设置左部文字大小
     */
    public void setLeftTextSize(@Dimension int sp) {
        this.mLeftTextSize = sp;
        if (mTvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set left text first.");
        } else {
            mTvLeft.setTextSize(mLeftTextSize);
        }
    }

    /**
     * 设置左部文字内图片padding
     *
     * @param padding
     */
    public void setLeftDrawablePadding(@Dimension int padding) {
        this.mLeftDrawablePadding = padding;
        if (mTvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set left text first.");
        } else {
            mTvLeft.setCompoundDrawablePadding(mLeftDrawablePadding);
        }
    }

    /**
     * 设置左部的文字内图片 右方向
     *
     * @param drawable
     */
    public void setLeftDrawableRight(Drawable drawable) {
        this.mLeftDrawableRight = drawable;
        if (mTvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set left text first.");
        } else {
            mTvLeft.setCompoundDrawables(mLeftDrawableRight, null, null, null);
        }
    }

    public void setLeftDrawableRight(@DrawableRes int resId) {
        setLeftDrawableRight(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * 设置左部的文字内图片 左方向
     *
     * @param drawable
     */
    public void setLeftDrawableLeft(Drawable drawable) {
        this.mLeftDrawableLeft = drawable;
        if (mTvLeft == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set left text first.");
        } else {
            mTvLeft.setCompoundDrawables(mLeftDrawableLeft, null, null, null);
        }
    }

    public void setLeftDrawableLeft(@DrawableRes int resId) {
        setLeftDrawableLeft(ContextCompat.getDrawable(mContext, resId));
    }


    /**
     * 设置右部图片
     */
    public void setRightImage(Drawable drawable) {
        this.mRightImage = drawable;
        if (mIvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! If you set the right text,The right ImageView won't be created.");

        } else {
            mIvRight.setImageDrawable(mRightImage);
        }
    }

    public void setRightImage(@DrawableRes int resId) {
        this.setRightImage(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * 设置右部文字
     */
    public void setRightText(String rightText) {
        this.mRightText = rightText;
        if (mTvRight == null) {
            setRightText();
            if (mIvRight != null) {
                mIvRight.setVisibility(GONE);
            }
        } else {
            mTvRight.setText(mRightText);
        }
    }

    /**
     * 设置右部文字
     */
    public void setRightText(@StringRes int resId) {
        setRightText(mContext.getResources().getText(resId).toString());
    }

    /**
     * 设置右部文字颜色
     */
    public void setRightTextColor(@ColorInt int color) {
        this.mRightTextColor = color;
        if (mTvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set right text first.");
        } else {
            mTvRight.setTextColor(mRightTextColor);
        }

    }

    /**
     * 设置右部文字大小
     */
    public void setRightTextSize(@Dimension int sp) {
        this.mRightTextSize = sp;
        if (mTvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set right text first.");
        } else {
            mTvRight.setTextSize(mRightTextSize);
        }
    }

    /**
     * 设置右部文字内图片padding
     *
     * @param padding
     */
    public void setRightDrawablePadding(@Dimension int padding) {
        this.mRightDrawablePadding = padding;
        if (mTvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set right text first.");
        } else {
            mTvRight.setCompoundDrawablePadding(mRightDrawablePadding);
        }
    }

    /**
     * 设置右部的文字内图片 右方向
     *
     * @param drawable
     */
    public void setRightDrawableLeft(Drawable drawable) {
        this.mRightDrawableLeft = drawable;
        if (mTvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set right text first.");
        } else {
            mTvRight.setCompoundDrawables(mRightDrawableLeft, null, null, null);
        }
    }

    public void setRightDrawableLeft(@DrawableRes int resId) {
        setRightDrawableLeft(ContextCompat.getDrawable(mContext, resId));
    }

    /**
     * 设置右部的文字内图片 左方向
     *
     * @param drawable
     */
    public void setRightDrawableRight(Drawable drawable) {
        this.mRightDrawableRight = drawable;
        if (mTvRight == null) {
            throw new IllegalArgumentException(
                    "NullPointException! Please set right text first.");
        } else {
            mTvRight.setCompoundDrawables(mRightDrawableRight, null, null, null);
        }
    }

    public void setRightDrawableRight(@DrawableRes int resId) {
        setRightDrawableRight(ContextCompat.getDrawable(mContext, resId));
    }


    /**
     * 设置背景颜色
     *
     * @param color color
     */
    public void setBackGroundColor(@ColorInt int color) {
        this.setBackgroundColor(color);
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
    private static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    private static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    private static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
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
            mDefPadding = dp2px(context, dp);
            return mConfig;
        }

        public Config setTitleTextSize(@Dimension int sp) {
            mDefTitleSize = sp;
            return mConfig;
        }

        public Config setTitleTextColor(@ColorInt int color) {
            mDefTitleColor = color;
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

        public Config setLeftImage(Context context, @DrawableRes int leftImageResId) {
            mDefLeftImage = ContextCompat.getDrawable(context, leftImageResId);
            return mConfig;
        }

        public Config setLeftTextSize(@Dimension int sp) {
            mDefLeftSize = sp;
            return mConfig;
        }

        public Config setRightTextSize(@Dimension int sp) {
            mDefRightSize = sp;
            return mConfig;
        }

        public Config setLeftTextColor(@ColorInt int color) {
            mDefLeftColor = color;
            return mConfig;
        }

        public Config setRightTextColor(@ColorInt int color) {
            mDefRightColor = color;
            return mConfig;
        }

    }

}
