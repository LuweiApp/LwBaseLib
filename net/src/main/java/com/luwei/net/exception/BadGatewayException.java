package com.luwei.net.exception;

/**
 * @author LiCheng
 * @date 2019/5/11
 */
public class BadGatewayException extends RuntimeException {

    private int code = 502;

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

    private String message = "服务器异常，请稍后再试";
}
