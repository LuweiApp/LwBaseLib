package com.luwei.ui;

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
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


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

    private boolean mIsBack;

    private LayoutParams mTitleParams;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private int mPadding;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mLeftTextSize;
    private int mRightTextSize;

    private String mTitleText;
    private String mLeftText;
    private String mRightText;

    private Drawable mLeftBackground;
    private Drawable mRightBackground;

    private TextView mTvTitle;
    private TextView mTvLeft;
    private TextView mTvRight;
    private ImageView mIvLeft;
    private ImageView mIvRight;


    private LeftClickListener mLeftListener;
    private RightClickListener mRightListener;

    public TitleBar(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public TitleBar(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initDefaultParam();
        getAttr(attrs);
        initTitle();
        initLeft();
        initRight();
    }

    private void initDefaultParam() {
        if (mDefPadding == 0)
            mDefPadding = dip2px(mContext, 15.0F);
        if (mDefTextSize == 0)
            mDefTextSize = 16;
        if (mDefTextColor == 0)
            mDefTextColor = Color.parseColor("#333333");


    }

    private void getAttr(AttributeSet attrs) {
        //从xml中获取属性
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mIsBack = ta.getBoolean(R.styleable.TitleBar_isBack, true);
        mPadding = ta.getDimensionPixelSize(R.styleable.TitleBar_padding, mDefPadding);
        mTitleTextColor = ta.getColor(R.styleable.TitleBar_titleColor, mDefTextColor);
        mTitleText = ta.getString(R.styleable.TitleBar_titleText);
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_titleTextSize, mDefTextSize);
        mLeftText = ta.getString(R.styleable.TitleBar_leftText);
        mLeftTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_leftTextSize, mDefTextSize);
        mLeftBackground = ta.getDrawable(R.styleable.TitleBar_leftBackground);
        mRightText = ta.getString(R.styleable.TitleBar_rightText);
        mRightTextSize = ta.getDimensionPixelSize(R.styleable.TitleBar_rightTextSize, mDefTextSize);
        mRightBackground = ta.getDrawable(R.styleable.TitleBar_rightBackground);
        ta.recycle();
    }

    private void initTitle() {
        //未设置标题时 获取Label作为标题
        if (mTitleText == null) {
            try {
                //获取manifest中label，设置为标题
                String label = getActivityLabel(getActivity());
                if(label!=null){
                    mTitleText = label;
                }else {
                    return;
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

        mTitleParams = new LayoutParams(w, h);
        mTitleParams.addRule(CENTER_IN_PARENT);
        mTvTitle.setGravity(Gravity.CENTER_VERTICAL);

        addView(mTvTitle, mTitleParams);
    }

    private void initLeft() {
        //未设置左部内容时直接返回
        if (!mIsBack && mLeftText == null && mLeftBackground == null)
            return;

        //isBack为true时，自动添加返回键
        if (mLeftBackground == null && mIsBack)
            mLeftBackground = getResources().getDrawable(R.mipmap.icon_back);

        mLeftParams = new LayoutParams(w, h);
        mLeftParams.addRule(ALIGN_PARENT_LEFT);

        if (mLeftBackground != null) {
            mIvLeft = new ImageView(mContext);
            mIvLeft.setImageDrawable(mLeftBackground);
            mIvLeft.setPadding(mPadding, 0, mPadding, 0);
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
        } else {
            mTvLeft = new TextView(mContext);
            mTvLeft.setText(mLeftText);
            mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftTextSize);
            mTvLeft.setGravity(Gravity.CENTER_VERTICAL);
            mTvLeft.setPadding(mPadding, 0, mPadding, 0);
            mTvLeft.setOnClickListener(new OnClickListener() {
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

            addView(mTvLeft, mLeftParams);

        }


    }

    private void initRight() {
        //未设置右部内容时直接返回
        if (mRightText == null && mRightBackground == null)
            return;


        mRightParams = new LayoutParams(w, h);
        mRightParams.addRule(ALIGN_PARENT_RIGHT);

        //判断在有设置背景图片时，只显示背景
        if (mRightBackground != null) {
            mIvRight = new ImageView(mContext);
            mIvRight.setPadding(mPadding, 0, mPadding, 0);
            mIvRight.setImageDrawable(mRightBackground);
            mIvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRightListener != null) {
                        mRightListener.rightClick();
                    }
                }
            });

            addView(mIvRight, mRightParams);
        } else {
            mTvRight = new TextView(mContext);
            mTvRight.setText(mRightText);
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, mRightTextSize);
            mTvRight.setGravity(Gravity.CENTER_VERTICAL);
            mTvRight.setPadding(mPadding, 0, mPadding, 0);
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

    }

    public void setTitleTextColor(int titleTextColor) {
        this.mTitleTextColor = titleTextColor;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.mTitleTextSize = titleTextSize;
    }

    public void setLeftTextSize(int leftTextSize) {
        this.mLeftTextSize = leftTextSize;
    }

    public void setRightTextSize(int rightTextSize) {
        this.mRightTextSize = rightTextSize;
    }

    public void setTitleText(String titleText) {
        this.mTitleText = titleText;
    }

    public void setLeftText(String leftText) {
        this.mLeftText = leftText;
    }

    public void setRightText(String rightText) {
        this.mRightText = rightText;
    }

    public void setLeftBackground(Drawable leftBackground) {
        this.mLeftBackground = leftBackground;
    }

    public void setRightBackground(Drawable rightBackground) {
        this.mRightBackground = rightBackground;
    }

    public void setLeftClickListener(LeftClickListener listener) {
        this.mLeftListener = listener;
    }

    public void setRightClickListener(RightClickListener listener) {
        this.mRightListener = listener;
    }

    public static Config getConfig(){
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
        if (activity == null)
            return;

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

    /**
     * 根据手机的分辨率sp 转成px(像素)
     */
    private static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5F);
    }


    public interface LeftClickListener {
        void leftClick();
    }

    public interface RightClickListener {
        void rightClick();
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
    }

}
