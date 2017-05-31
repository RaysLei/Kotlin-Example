package com.rays.kotlinexample.entity

/**
 * Created by Rays on 2017/5/27.
 */
class Daily(
        var _id: String? = null,
        var desc: String? = null,
        var content: String? = null,
        var created_at: String? = null,
        var publishedAt: String? = null,
        var rand_id: String? = null,
        var title: String? = null,
        var updated_at: String? = null
) {
    val imgUrl: String
        get() {
            content?.let {
                val start = content!!.indexOf("src=\"").plus(5)
                val end = content!!.indexOf(".jpg").plus(4)
                return content!!.substring(start, end)
            }
            return ""
        }

    val date: String
        get() {
            publishedAt?.let {
                val end = publishedAt!!.indexOf("T")
                return publishedAt!!.substring(0, end)
            }
            return ""
        }
}