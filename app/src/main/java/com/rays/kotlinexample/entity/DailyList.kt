package com.rays.kotlinexample.entity

/**
 * Created by Rays on 2017/5/31.
 */
data class DailyList(
        var Android: List<GanHuoData>? = null,
        var iOS: List<GanHuoData>? = null,
        var 休息视频: List<GanHuoData>? = null,
        var 拓展资源: List<GanHuoData>? = null,
        var 瞎推荐: List<GanHuoData>? = null,
        var 福利: List<GanHuoData>? = null,
        var App: List<GanHuoData>? = null,
        var 前端: List<GanHuoData>? = null
)