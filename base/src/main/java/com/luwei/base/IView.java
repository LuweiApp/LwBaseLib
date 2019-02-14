package com.luwei.base;

/**
 * Created by wanglei on 2016/12/29.
 * <p>
 * 修改：pht   添加网络异常缺省页
 */


public interface IView<P extends IPresent> {

    P newP();

    P getP();

    void showLoading();

    void hideLoading();

}
