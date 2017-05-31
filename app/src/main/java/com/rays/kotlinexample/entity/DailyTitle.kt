package com.rays.kotlinexample.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Rays on 2017/5/31.
 */
data class DailyTitle(val title: String) : MultiItemEntity {

    override fun getItemType(): Int {
        return DailyItemType.TITLE.ordinal
    }

}