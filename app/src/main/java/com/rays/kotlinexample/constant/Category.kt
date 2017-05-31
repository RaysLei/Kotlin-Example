package com.rays.kotlinexample.constant

import com.rays.kotlinexample.entity.CategoryEntity

/**
 * Created by Rays on 2017/5/31.
 */
object Category {
    val ganHuoCategory = arrayOf(
            "Android",
            "iOS",
            "前端",
            "拓展资源",
            "瞎推荐",
            "福利",
            "休息视频"
    )

    val cateMaps = mapOf(
            "休息视频" to "http://omqomam0q.bkt.clouddn.com/video.png",
            "Android" to "http://omqomam0q.bkt.clouddn.com/android.png",
            "iOS" to "http://omqomam0q.bkt.clouddn.com/ios.png",
            "前端" to "http://omqomam0q.bkt.clouddn.com/qianduan.png",
            "拓展资源" to "http://omqomam0q.bkt.clouddn.com/tuozhan.png",
            "瞎推荐" to "http://omqomam0q.bkt.clouddn.com/xiatuijian.png",
            "福利" to "http://omqomam0q.bkt.clouddn.com/meizhi.png"
    )

    fun getGanHuoCategory(): List<CategoryEntity> {
        return ganHuoCategory.mapTo(ArrayList<CategoryEntity>()) { CategoryEntity(it, cateMaps[it] ?: "") }
    }
}