package com.rays.kotlinexample.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by Rays on 2017/6/2.
 */
data class CategoryItem(val data: GanHuoData, val type: Int) : MultiItemEntity {
    companion object {
        val TYPE_TEXT = 0
        val TYPE_IMG = 1
    }

    override fun getItemType() = if (type == TYPE_TEXT) TYPE_TEXT else TYPE_IMG
}