package com.luwei.lwbaselib.module.recyclerview.adapter;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/11/15
 */
public class RichTextBean {

    private int imgRes;

    private String content;

    public RichTextBean(int imgRes,String content){
        this.imgRes = imgRes;
        this.content = content;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
