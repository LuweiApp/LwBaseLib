package cn.luwei.mvp.net.exception;

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

    private String message="部分功能被禁用，请联系客服！";
}
