package com.umeng.umlibrary.listener;

import java.util.Map;

/**
 * @author LiCheng
 * @date 2019/3/4
 */
public interface SimpleAuthorizeListener {

    void onComplete(Map<String, String> infoMap);

    void onError(Throwable t);
}
