package com.luwei.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.luwei.ui.R;

import java.util.Locale;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/11
 */
public class TimerButton extends android.support.v7.widget.AppCompatButton {

    public static final String TAG = "TimerButton";

    private CountDownTimer mTimer;
    private String mFormatText;
    private String mOriginalText;
    private String mFinishedText;
    private int mOriginalColor;
    private int mStartedColor;
    private Callback mCallback;
    private boolean isStated = false;

    public TimerButton(Context context) {
        this(context, null);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mOriginalText = getText().toString();
        mOriginalColor = getCurrentTextColor();
        initAttrs(context, attrs);
        initTimer();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimerButton);
        mFormatText = array.getString(R.styleable.TimerButton_formatText);
        mFinishedText = array.getString(R.styleable.TimerButton_finishedText);
        mStartedColor = array.getColor(R.styleable.TimerButton_startedTextColor,mOriginalColor);
        array.recycle();
    }


    private void initTimer() {
        mTimer = new CountDownTimer(2 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG,""+millisUntilFinished);
                try {
                    setText(String.format(Locale.CHINA, mFormatText, millisUntilFinished / 1000));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    setText(String.format(Locale.CHINA, "%d", millisUntilFinished / 1000));
                }
                if (mCallback != null) {
                    mCallback.onTick(TimerButton.this, millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                setTextColor(mOriginalColor);
                setText(TextUtils.isEmpty(mFinishedText) ? mOriginalText : mFinishedText);
                if (mCallback != null) {
                    mCallback.onFinish(TimerButton.this);
                }
                isStated = false;
            }
        };
    }

    public boolean isStated() {
        return isStated;
    }

    public void resetStatus(){
        setTextColor(mOriginalColor);
        setText(mOriginalText);
        isStated = false;
    }

    public void start() {
        if (isStated) {
            return;
        } else {
            initTimer();
        }
        isStated = true;
        setTextColor(mStartedColor);
        mTimer.start();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onDetachedFromWindow() {
        resetStatus();
        mTimer.cancel();
        mTimer = null;
        super.onDetachedFromWindow();
    }

    public interface Callback {
        void onFinish(TimerButton button);

        void onTick(TimerButton button, long millisUntilFinished);
    }

}
