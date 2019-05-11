package com.luwei.net.Interceptor;


import com.luwei.net.exception.BIZexception;
import com.luwei.net.exception.BadGatewayException;
import com.luwei.net.exception.NetCreatedException;
import com.luwei.net.exception.NetForbiddenException;
import com.luwei.net.exception.NetNotFoundException;
import com.luwei.net.exception.NetUnauthorizedException;
import com.luwei.net.exception.NoNetException;
import com.luwei.net.exception.ServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 网络拦截器，返回异常进行集中处理
 */
public class NetWorkInterceptor implements Interceptor {

    //错误HTTP状态码
    private final int CREATE_CODE = 201;
    private final int UNAUTHORIZED_CODE = 401;
    private final int FORBIDDEN_CODE = 403;
    private final int NOTFOUND_CODE = 404;
    private final int SERVICEERROR_CODE = 500;
    private final int BADGATEWAY_CODE = 502;

    private final int NONET_CODE = 504;
    //业务异常
    private final int BIZ_CODE = 400;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        //拦截器
        switch (response.code()) {
            case CREATE_CODE:
                throw new NetCreatedException();
            case UNAUTHORIZED_CODE:
                throw new NetUnauthorizedException();
            case FORBIDDEN_CODE:
                throw new NetForbiddenException();
            case NOTFOUND_CODE:
                throw new NetNotFoundException();
            case NONET_CODE:
                throw new NoNetException();
            case SERVICEERROR_CODE:
                throw new ServiceException();
            case BADGATEWAY_CODE:
                throw new BadGatewayException();
            case BIZ_CODE:
                //业务异常，取出其中描述，抛异常
                try {
                    String body = response.body().string();
                    String code = new JSONObject(body).getString("code");
                    String msg = new JSONObject(body).getString("msg");
                    throw new BIZexception(code, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }


        return response;
    }

}
