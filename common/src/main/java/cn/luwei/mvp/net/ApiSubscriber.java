package cn.luwei.mvp.net;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.UnknownHostException;

import cn.luwei.mvp.net.exception.BIZexception;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by wanglei on 2016/12/26.
 */

public abstract class ApiSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        NetError error = null;
        if (e != null) {
            if (!(e instanceof NetError)) {
                if (e instanceof UnknownHostException) {
                    error = new NetError(e, NetError.NoConnectError);
                } else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetError(e, NetError.ParseError);
                } else if (e instanceof BIZexception){
                    error = new NetError(e, NetError.BusinessError);
                }else{
                    error = new NetError(e, NetError.OtherError);
                }
            } else {
                error = (NetError) e;
            }

            if (useCommonErrorHandler()
                    && XApi.getCommonProvider() != null) {
                if (XApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
                    return;
                }
            }
            onFail(error);
        }

    }

    protected abstract void onFail(NetError error);

    @Override
    public void onComplete() {

    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
