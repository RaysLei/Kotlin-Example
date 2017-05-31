package common.framework.network;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Rays on 2017/3/29.
 */
public class ServiceFactory {

    private OkHttpClient okHttpClient;

    private ServiceFactory(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public static ServiceFactory getInstance() {
        return new ServiceFactory(OkHttpProvider.getInstance().getDefaultOkHttpClient());
    }

    public static ServiceFactory getNoCacheInstance() {
        return new ServiceFactory(OkHttpProvider.getInstance().getNetWorkOkHttpClient());
    }

    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

}
