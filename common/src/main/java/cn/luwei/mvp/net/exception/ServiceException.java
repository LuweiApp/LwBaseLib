package cn.luwei.mvp.net.exception;

/**
 * Created by LW-PC0921 on 2017/12/4.
 */

public class ServiceException extends RuntimeException {

    private int code=500;

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

    private String message="服务器异常，请稍后再试！";
}
