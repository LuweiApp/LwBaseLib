package cn.luwei.mvp.net.exception;

/**
 * Created by LW-PC0921 on 2017/11/24.
 */

public class NoNetException extends RuntimeException {
    private int code=504;

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

    private String message="网络异常，请检查网络";
}
