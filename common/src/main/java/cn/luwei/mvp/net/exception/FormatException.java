package cn.luwei.mvp.net.exception;

/**
 * Created by Pht on 2016/12/22.
 */

public class FormatException extends RuntimeException {

    public int code = -200;
    public String message = "转换数据格式异常";

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
}
