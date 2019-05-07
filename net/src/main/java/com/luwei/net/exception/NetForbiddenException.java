package com.luwei.net.exception;

/**
 * Created by Pht on 2017/9/26.
 */

public class NetForbiddenException extends RuntimeException {
    private int code=403;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message="部分服务器资源暂不可用";
}
