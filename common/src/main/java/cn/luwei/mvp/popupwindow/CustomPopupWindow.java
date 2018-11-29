package cn.luwei.mvp.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import cn.luwei.mvp.R;

/**
 * Created by LiCheng
 * Date：2018/11/13
 */
public class CustomPopupWindow {

    private Context mContext;
    private PopupWindow mPopupWindow;


    //设置 PopupWindow的宽度
    private int mWidth;
    //设置 PopupWindow的高度
    private int mHeight;
    //设置 PopupWindow  内展示的内容view
    private View mContentView;
    //设置 PopupWindow  内展示的内容layoutId
    private int mResLayoutId = -1;
    //设置 PopupWindow是否可触摸
    private boolean mTouchable = true;
    //设置 点击 PopupWindow以外区域，隐藏 popupWindow
    private boolean mOutsideTouchable = true;
    //设置 PopupWindow 的背景。该属性不设置会导致 PopupWindow 出现后不会消失，即便是 点击 back 键也不起作用。
    private Drawable mBackgroundDrawable;
    //设置动画特效 即 展示和消失动画
    private int mAnimationStyle = -1;
    //主要作用是为了设置 PopupWindow 显示的时候是否会与 StatusBar 重叠（如果存在的话也包括 SystemBar ）系统自己设定 api>=22就设置为true，反之false
    private boolean mAttachedInDecor = true;
    //设置 PopupWindow 允许超出窗口
    private boolean mClippingEnabled = true;
    //设置 PopupWindow  的高度，类似于 3D 效果的阴影
    private float mElevation = -1;
    //设置 PopupWindow的入场动画
    private Transition mEnterTransition;
    //设置 PopupWindow的出场动画
    private Transition mExitTransition;
    //设置 popupWindow 是否可以获取焦点
    private boolean mFocusable = true;
    //设置输入法的操作模式
    private int mInputMethodMode = -1;
    //监听 PopupWindow关闭的事件
    private PopupWindow.OnDismissListener mOnDismissListener;
    //PopupWindow触摸时的监听回调
    private View.OnTouchListener mTouchInterceptor;
    //设置 PopupWindow布局类型
    private int mWindowLayoutType = -1;

    private CustomPopupWindow(Context context) {
        mContext = context;
    }

    /**
     * 获取宽度 需要计算 否则返回-2（WRAP)或者-1（MATCH)
     * @return mWidth = mPopupWindow.getContentView().getMeasuredWidth();
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * 获取高度 需要计算 否则返回-2（WRAP)或者-1（MATCH)
     * @return mHeight = mPopupWindow.getContentView().getMeasuredHeight;
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * 判断是否显示
     * @return
     */
    public boolean isShowing(){
        return mPopupWindow.isShowing();
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss(){
        if (mPopupWindow!=null){
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
                mOnDismissListener = null;
            }
        }
    }

    public CustomPopupWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    public CustomPopupWindow showAsDropDown(View anchor, int xoff, int yoff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        }
        return this;
    }

    /**
     * 以选定的View的左下角作为锚点显示，可以输入XY坐标值改变位置
     *
     * @param anchor  PopupWindow确定锚点位置的View
     * @param xoff    PopupWindow相对锚点的x偏移
     * @param yoff    PopupWindow相对锚点的y偏移
     * @param gravity
     * @return
     */
    public CustomPopupWindow showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        }
        return this;
    }

    /**
     * 直接以传入的View为幕布，在其中选择位置显示（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等）
     *
     * @param parent  PopupWindow的父View
     * @param gravity PopupWindow相对父View的位置
     * @param x       PopupWindow相对父View的x偏移
     * @param y       PopupWindow相对父View的y偏移
     * @return
     */
    public CustomPopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }


    private PopupWindow build() {
        //传入layoutId时转成View
        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }
        //创建PopupWindow对象，未设置size时设为wrap大小
        if(mWidth==0){
            mWidth =ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        if(mHeight ==0){
            mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);

        initPopupWindow(mPopupWindow);

        //如未设置宽高，计算初始化后PopupWindow的宽高，传入mWidth和mHight中
        mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if(mWidth==ViewGroup.LayoutParams.WRAP_CONTENT){
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
        }
        if(mHeight ==ViewGroup.LayoutParams.WRAP_CONTENT){
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }
        mPopupWindow.update();


        return mPopupWindow;
    }


    private void initPopupWindow(PopupWindow popupWindow) {
        popupWindow.setTouchable(mTouchable);
        popupWindow.setFocusable(mFocusable);
        popupWindow.setOutsideTouchable(mOutsideTouchable);
        popupWindow.setClippingEnabled(mClippingEnabled);

        if (mInputMethodMode != -1) {
            popupWindow.setInputMethodMode(mInputMethodMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mTouchInterceptor != null) {
            popupWindow.setTouchInterceptor(mTouchInterceptor);
        }
        //默认为透明色
        if (mBackgroundDrawable == null) {
            mBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        }
        popupWindow.setBackgroundDrawable(mBackgroundDrawable);

        //默认添加淡入淡出动画
        if (mAnimationStyle == -1) {
            mAnimationStyle = R.style.CustomPopupStyle;
        }
        popupWindow.setAnimationStyle(mAnimationStyle);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            popupWindow.setAttachedInDecor(mAttachedInDecor);
        } else {
            Log.e("PopupWindowError",
                    "PopupWindowError:setAttachedInDecor 需要api > " + Build.VERSION_CODES.LOLLIPOP_MR1);
        }

        if (mElevation != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(mElevation);
            } else {
                Log.e("PopupWindowError",
                        "PopupWindowError:setElevation 需要api > " + Build.VERSION_CODES.LOLLIPOP);
            }
        }

        if (mEnterTransition != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupWindow.setEnterTransition(mEnterTransition);
            } else {
                Log.e("PopupWindowError",
                        "PopupWindowError:setEnterTransition 需要api > " + Build.VERSION_CODES.M);
            }
        }

        if (mExitTransition != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupWindow.setExitTransition(mExitTransition);
            } else {
                Log.e("PopupWindowError",
                        "PopupWindowError:setExitTransition 需要api > " + Build.VERSION_CODES.M);
            }
        }

        if (mWindowLayoutType != -1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupWindow.setWindowLayoutType(mWindowLayoutType);
            } else {
                Log.e("PopupWindowError",
                        "PopupWindowError:setWindowLayoutType 需要api > " + Build.VERSION_CODES.M);
            }
        }
    }


    public static class Builder {
        private CustomPopupWindow mCustomPopupWindow;

        public Builder(@NonNull Context context) {
            mCustomPopupWindow = new CustomPopupWindow(context);

        }

        //设置 PopupWindow 展示的内容view
        public Builder setView(View contentView) {
            mCustomPopupWindow.mContentView = contentView;
            mCustomPopupWindow.mResLayoutId = -1;
            return this;
        }

        //设置 PopupWindow 展示的内容layoutId
        public Builder setView(int resLayoutId) {
            mCustomPopupWindow.mResLayoutId = resLayoutId;
            mCustomPopupWindow.mContentView = null;
            return this;
        }

        //设置 PopupWindow 的尺寸
        public Builder setSize(int width, int hight) {
            mCustomPopupWindow.mWidth = width;
            mCustomPopupWindow.mHeight = hight;
            return this;
        }

        //设置 popupWindow 是否可以获取焦点
        public Builder setFocusable(boolean focusable) {
            mCustomPopupWindow.mFocusable = focusable;
            return this;
        }

        //监听 PopupWindow关闭的事件
        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            mCustomPopupWindow.mOnDismissListener = onDismissListener;
            return this;
        }

        //设置 点击 PopupWindow意外区域，隐藏 popupWindow  然而并没有什么卵用
        public Builder setOutsideTouchable(boolean touchable) {
            mCustomPopupWindow.mOutsideTouchable = touchable;
            return this;
        }

        //设置 PopupWindow是否可触摸
        public Builder setTouchable(boolean touchable) {
            mCustomPopupWindow.mTouchable = touchable;
            return this;
        }

        //设置动画特效 即 展示和消失动画
        public Builder setAnimationStyle(int animationStyle) {
            mCustomPopupWindow.mAnimationStyle = animationStyle;
            return this;
        }

        //主要作用是为了设置 PopupWindow 显示的时候是否会与 StatusBar 重叠（如果存在的话也包括 SystemBar ）
        public Builder setAttachedInDecor(boolean enabled) {
            mCustomPopupWindow.mAttachedInDecor = enabled;
            return this;
        }

        //设置  PopupWindow 的背景。该属性不设置的会，会导致 PopupWindow 出现后不会消失，即便是 点击 back 键也不起作用。这应该是 PopupWindow 较为变态的地方。
        public Builder setBackgroundDrawable(Drawable background) {
            mCustomPopupWindow.mBackgroundDrawable = background;
            return this;
        }

        //设置 PopupWindow 允许超出窗口
        public Builder setClippingEnabled(boolean enabled) {
            mCustomPopupWindow.mClippingEnabled = enabled;
            return this;
        }

        //设置 PopupWindow  的高度，类似于 3D 效果的阴影
        public Builder setElevation(float elevation) {
            mCustomPopupWindow.mElevation = elevation;
            return this;
        }

        //设置 PopupWindow的入场动画
        public Builder setEnterTransition(Transition enterTransition) {
            mCustomPopupWindow.mEnterTransition = enterTransition;
            return this;
        }

        //有如就得有出 设置出场动画
        public Builder setExitTransition(Transition exitTransition) {
            mCustomPopupWindow.mExitTransition = exitTransition;
            return this;
        }

        //脸颊事件  Events 都是有大小的当触摸点大于手指头大小时，则为 脸颊事件 ，蛮有意思的 你可以尝试一下。
        public Builder setIgnoreCheekPress() {
            mCustomPopupWindow.mPopupWindow.setIgnoreCheekPress();
            return this;
        }

        //设置输入法的操作模式
        public Builder setInputMethodMode(int mode) {
            mCustomPopupWindow.mInputMethodMode = mode;
            return this;
        }


        // PopupWindow触摸时的监听回调
        public Builder setTouchInterceptor(View.OnTouchListener l) {
            mCustomPopupWindow.mTouchInterceptor = l;
            return this;
        }

        //设置 PopupWindow布局类型
        public Builder setWindowLayoutType(int layoutType) {
            mCustomPopupWindow.mWindowLayoutType = layoutType;
            return this;
        }

        //构建PopupWindow
        public CustomPopupWindow create() {
            mCustomPopupWindow.build();
            return mCustomPopupWindow;
        }
    }

}
