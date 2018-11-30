package com.luwei.net;

import java.io.Serializable;

/**
 * Created by Ly on 2017/8/14.
 */

public class LwBaseBean implements IModel, Serializable {

    private String message;
    private boolean isBizError;
    private boolean isNull;

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public void setBizError(boolean bizError) {
        isBizError = bizError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean isNull() {
        return isNull;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return isBizError;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
