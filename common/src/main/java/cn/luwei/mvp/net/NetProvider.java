package cn.luwei.mvp.net;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;

/**
 * Created by wanglei on 2016/12/24.
 */

public interface NetProvider {
    Interceptor[] configInterceptors();

    void configHttps(OkHttpClient.Builder builder);

    CookieJar configCookie();

    RequestHandler configHandler();

    long configConnectTimeoutMills();

    long configReadTimeoutMills();

    boolean configLogEnable();

    boolean handleError(NetError error);

    Converter.Factory configConverterFactory();
}
