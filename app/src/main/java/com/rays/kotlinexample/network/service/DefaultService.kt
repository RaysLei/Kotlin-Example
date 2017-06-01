package com.rays.kotlinexample.network.service

import com.rays.kotlinexample.entity.Daily
import com.rays.kotlinexample.entity.DailyList
import com.rays.kotlinexample.entity.GanHuoData
import com.rays.kotlinexample.entity.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rays on 2017/5/27.
 */
interface DefaultService {
    companion object {
        val baseUrl = "http://www.gank.io/api/"
    }

    @GET("history/content/10/{pageIndex}")
    fun getRecentlyList(@Path("pageIndex") pageIndex: Int): Observable<HttpResult<List<Daily>>>

    /**
     * 获取某天的干货
     */
    @GET("day/{date}")
    fun getRecentlyByDate(@Path("date") date: String): Observable<HttpResult<DailyList>>

    /**
     * 根据类别查询干货
     */
    @GET("data/{category}/20/{pageIndex}")
    fun getGanHuo(@Path("category") category: String, @Path("pageIndex") pageIndex: Int): Observable<HttpResult<List<GanHuoData>>>
}
