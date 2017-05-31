package common.framework.network;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import common.framework.BaseApplication;
import common.framework.util.NetworkUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Rays on 2017/3/29.
 */
public class OkHttpProvider {
    private OkHttpClient okHttpClient;
    private OkHttpClient networkOkHttpClient;

    /**
     * 断网后，默认缓存30天
     */
    private static final CacheControl CACHE_CONTROL_FORCE_CACHE = new CacheControl.Builder()
            .onlyIfCached()
            .maxStale(30, TimeUnit.DAYS)
            .build();

    private static class SingletonHolder {
        private static OkHttpProvider INSTANCE = new OkHttpProvider();
    }

    public static OkHttpProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private OkHttpProvider() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时时间
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        // 设置缓存路径及大小
        File file = new File(getHttpCacheDir(), "OkHttpCache");
        Cache cache = new Cache(file, 50 << 20);
        builder.cache(cache);
        // add interceptor
        addInterceptor(builder, new CacheControlInterceptor());
        okHttpClient = builder.build();
    }

    public OkHttpClient getDefaultOkHttpClient() {
        return okHttpClient;
    }

    public OkHttpClient getNetWorkOkHttpClient() {
        if (networkOkHttpClient == null) {
            OkHttpClient.Builder builder = okHttpClient.newBuilder();
            builder.interceptors().clear();
            builder.networkInterceptors().clear();
            // add interceptor
            addInterceptor(builder, new FromNetWorkControlInterceptor());
            networkOkHttpClient = builder.build();
        }
        return networkOkHttpClient;
    }

    private void addInterceptor(OkHttpClient.Builder builder, Interceptor interceptor) {
        // add logging interceptor
        if (BaseApplication.getInstance().isDebug()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(interceptor);
        builder.addNetworkInterceptor(interceptor);
    }

    /**
     * 强制从网络获取数据
     */
    private static class FromNetWorkControlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 缓存拦截器，如果设置了缓存且无网络时，强制读取缓存
     */
    private static class CacheControlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            CacheControl cacheControl = request.cacheControl();
            // 没有网络且设有缓存时，获取缓存数据
            if (!NetworkUtil.isConnected(BaseApplication.getInstance()) &&
                    (cacheControl.maxAgeSeconds() > 0 || cacheControl.maxStaleSeconds() > 0)) {
                request = request.newBuilder()
                        .cacheControl(CACHE_CONTROL_FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            String cacheControlStr = request.cacheControl().toString();
            if (!TextUtils.isEmpty(cacheControlStr)) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControlStr)
                        .build();
            }
            return response;
        }
    }

    /*private static class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LogUtil.d(TAG, String.format(Locale.CHINA, "Sending request %s on %s\n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtil.d(TAG, String.format(Locale.CHINA, "Received response for %s in %.1fms\n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }*/

    /**
     * 获取缓存路径
     *
     * @return
     */
    private File getHttpCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = BaseApplication.getInstance().getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return BaseApplication.getInstance().getCacheDir();
    }
}
