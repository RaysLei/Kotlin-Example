package com.rays.kotlinexample.network.service

import common.framework.network.OkHttpProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Rays on 2017/3/29.
 */
class ServiceFactory private constructor(private val okHttpClient: OkHttpClient) {

    fun <S> createService(clazz: Class<S>, baseUrl: String): S {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(clazz)
    }

    companion object {
        val instance: ServiceFactory
            get() = ServiceFactory(OkHttpProvider.getInstance().defaultOkHttpClient)

        val noCacheInstance: ServiceFactory
            get() = ServiceFactory(OkHttpProvider.getInstance().netWorkOkHttpClient)
    }

}
