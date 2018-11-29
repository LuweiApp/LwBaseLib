package com.luwei.base.bus;

/**
 * Created by Tomey at 2018/6/19
 */
public interface IEvent{

    int getFlag();

    <T> T getContent();

}
