package com.luwei.lwbaselib.bean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @author LiCheng
 * @date 2019-06-27
 * 首页导航binder需要用到bean
 */
public class MainNaviBean {

    private String title;
    private Jumper jumper;

    public MainNaviBean(String title, Class<? extends Activity> to) {
        this(title, new Jumper() {
            @Override
            public void jump(Context context, MainNaviBean bean) {
                context.startActivity(new Intent(context, to));
            }
        });
    }

    private MainNaviBean(String title, Jumper jumper) {
        this.title = title;
        this.jumper = jumper;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Jumper getJumper() {
        return jumper;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }

    public interface Jumper {
        /**
         * 跳转
         *
         * @param context
         * @param bean
         */
        void jump(Context context, MainNaviBean bean);
    }
}
