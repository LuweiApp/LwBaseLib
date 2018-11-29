package cn.luwei.mvp.net.exception;

/**
 * Created by SEELE on 2017/9/26.
 */

public class BIZexception extends RuntimeException {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BIZexception(String code,String desc) {
        this.code = code;
        this.message=desc;
    }


}
