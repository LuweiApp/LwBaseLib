package cn.luwei.mvp.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.luwei.mvp.R;


/**
 * @author runla
 * @date 2018/8/1
 * 文件描述：dialog 基础封装
 */

public abstract class BaseDialog extends DialogFragment {

    private int mGravity = Gravity.CENTER;
    private boolean mCanCancel = true;
    private boolean mTransparent = false;
    private int mLeftMargin = 0;
    private int mRightMargin = 0;
    private int mTopMargin = 0;
    private int mBottomMargin = 0;
    private int mAnimationsStyleId = 0;
    // dialog 屏幕宽度的百分比
    private float mDialogWidthPercent = 1f;
    protected ConfirmListener mConfirmLitener;
    protected CancelListener mCancelListener;


    protected static final String TAG = BaseDialog.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(1, R.style.dialog);

        if (savedInstanceState != null) {
            this.mLeftMargin = savedInstanceState.getInt("mLeftMargin");
            this.mTopMargin = savedInstanceState.getInt("mTopMargin");
            this.mRightMargin = savedInstanceState.getInt("mRightMargin");
            this.mBottomMargin = savedInstanceState.getInt("mBottomMargin");
            this.mGravity = savedInstanceState.getInt("mGravity");
            this.mAnimationsStyleId = savedInstanceState.getInt("mAnimationsStyleId");
            this.mTransparent = savedInstanceState.getBoolean("mTransparent");
            this.mCanCancel = savedInstanceState.getBoolean("mCanCancel");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mLeftMargin", mLeftMargin);
        outState.putInt("mRightMargin", mRightMargin);
        outState.putInt("mTopMargin", mTopMargin);
        outState.putInt("mBottomMargin", mBottomMargin);
        outState.putInt("mGravity", mGravity);
        outState.putInt("mAnimationsStyleId", mAnimationsStyleId);
        outState.putBoolean("mCanCancel", mCanCancel);
        outState.putBoolean("mTransparent", mTransparent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //设置背景透明
        View view = inflater.inflate(getLayoutId(), container, false);
        onCreateView(DialogViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
        initEvent();
    }

    private void initParams() {
        Window window = this.getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mGravity;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 设置 dialog 是否可以取消
        this.setCanCancel(mCanCancel);
        getDialog().setCanceledOnTouchOutside(mCanCancel);


        // 设置中间显示的时候，设置左右边距
        if (params.gravity == Gravity.CENTER && mDialogWidthPercent == 1) {
            mDialogWidthPercent = 0.75f;
        }

        // 设置动画样式
        if (params.gravity == Gravity.TOP) {
            window.setWindowAnimations(R.style.TopActionSheetDialogAnimation);
        }
        if (mAnimationsStyleId != 0) {
            window.setWindowAnimations(mAnimationsStyleId);
        }

        // 设置 dialog 的内边距
        window.getDecorView().setPadding(mLeftMargin, mTopMargin, mRightMargin, mBottomMargin);


        // 设置透明度
        if (mTransparent) {
            params.dimAmount = 0.0f;
        }

        window.setAttributes(params);

        // 设置 dialog 的屏幕宽度占比
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int) (dm.widthPixels * mDialogWidthPercent), ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void initEvent() {

    }


    public BaseDialog setTransparent(boolean mTransparent) {
        this.mTransparent = mTransparent;
        return this;
    }

    public BaseDialog setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    /**
     * 弹窗的动画样式
     * <style name="BottomActionSheetDialogAnimation" parent="@android:style/Animation.Dialog">
     * <item name="android:windowEnterAnimation">@anim/bottom_anim_dialog_in</item>
     * <item name="android:windowExitAnimation">@anim/bottom_anim_dialog_out</item>
     * </style>
     *
     * @param mAnimationsStyleId
     * @return
     */
    public BaseDialog setAnimationsStyleId(int mAnimationsStyleId) {
        this.mAnimationsStyleId = mAnimationsStyleId;
        return this;
    }

    public BaseDialog setCanCancel(boolean mCanCancel) {
        this.mCanCancel = mCanCancel;
        return this;
    }

    public BaseDialog setConfirmLitener(ConfirmListener mConfirmLitener) {
        this.mConfirmLitener = mConfirmLitener;
        return this;
    }

    public BaseDialog setCancelListener(CancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;
        return this;
    }

    /**
     * 单位为 dp
     *
     * @param leftMargin
     * @return
     */
    protected BaseDialog setLeftMargin(int leftMargin) {
        this.mLeftMargin = dp2px(leftMargin);
        return this;
    }

    /**
     * 单位为 dp
     *
     * @param rightMargin
     * @return
     */
    protected BaseDialog setRightMargin(int rightMargin) {
        this.mRightMargin = dp2px(rightMargin);
        return this;
    }


    /**
     * 单位为 dp
     *
     * @param topMargin
     * @return
     */
    protected BaseDialog setTopMargin(int topMargin) {
        this.mTopMargin = dp2px(topMargin);
        return this;
    }


    /**
     * 单位为 dp
     *
     * @param bottomMargin
     * @return
     */
    protected BaseDialog setBottomMargin(int bottomMargin) {
        this.mBottomMargin = dp2px(bottomMargin);
        return this;
    }

    public BaseDialog setDialogWidthPercent(float mDialogWidthPercent) {
        this.mDialogWidthPercent = mDialogWidthPercent;
        return this;
    }

    public int dp2px(float dipValue) {
        float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }


    public void showDialog(AppCompatActivity appCompatActivity) {
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        if (!appCompatActivity.isFinishing()
                && !this.isAdded()) {

            fragmentManager.beginTransaction()
                    .add(this, TAG)
                    .commitAllowingStateLoss();

        }
    }

    protected abstract int getLayoutId();

    public abstract void onCreateView(DialogViewHolder viewHolder, BaseDialog dialog);

}
