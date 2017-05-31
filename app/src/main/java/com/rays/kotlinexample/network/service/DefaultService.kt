package com.rays.kotlinexample.network.service

import com.rays.kotlinexample.entity.Daily
import com.rays.kotlinexample.entity.DailyList
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

    @GET("day/{date}")
    fun getRecentlyByDate(@Path("date") date: String): Observable<HttpResult<DailyList>>
}
