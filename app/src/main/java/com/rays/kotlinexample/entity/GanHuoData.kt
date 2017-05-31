package com.rays.kotlinexample.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Rays on 2017/5/31.
 */
data class GanHuoData(
        var _id: String? = null,
        var createdAt: String? = null,
        var desc: String? = null,
        var images: List<String>? = null,
        var publishedAt: String? = null,
        var source: String? = null,
        var type: String? = null,
        var url: String? = null,
        var used: Boolean? = null,
        var who: String? = null
) : MultiItemEntity {

    override fun getItemType(): Int {
        return DailyItemType.CONTENT.ordinal
    }

}