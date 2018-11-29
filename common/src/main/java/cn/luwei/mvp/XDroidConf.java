package cn.luwei.mvp;

import cn.luwei.mvp.router.Router;

/**
 * Created by wanglei on 2016/12/4.
 */

public class XDroidConf {
    // #log
    public static final boolean LOG = true;
    public static final String LOG_TAG = "XDroid";

    // #cache
    public static final String CACHE_SP_NAME = "config";
    public static final String CACHE_DISK_DIR = "cache";

    // #router
    public static final int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static final int ROUTER_ANIM_EXIT = Router.RES_NONE;

    // #dev model
    public static final boolean DEV = true;
}