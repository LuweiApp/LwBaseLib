package cn.luwei.mvp.net.Interceptor;

import java.io.IOException;

import cn.luwei.mvp.net.RequestHandler;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 网络请求 request 和 response 拦截器
 */
public class XInterceptor implements Interceptor {

    RequestHandler handler;

    public XInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            Response tmp = handler.onAfterRequest(response, chain);
            if (tmp != null) {
                return tmp;
            }

        }
        return response;
    }
}
