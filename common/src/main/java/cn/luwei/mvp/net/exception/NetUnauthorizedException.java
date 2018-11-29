package cn.luwei.mvp.net.exception;

/**
 * Created by Pht on 2017/9/26.
 */

public class NetUnauthorizedException extends RuntimeException {
    private int code=401;

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

    private String message="您的账号已在别处登录";
}
