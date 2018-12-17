package com.luwei.rxbus;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/28
 */
public class BaseEvent implements IEvent {

    private Object mContent;
    private int mFlag;

    public BaseEvent(int flag, Object content) {
        this.mContent = content;
        this.mFlag = flag;
    }

    @Override
    public int getFlag() {
        return mFlag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public  <T> T getContent() {
        return (T) mContent;
    }
}
