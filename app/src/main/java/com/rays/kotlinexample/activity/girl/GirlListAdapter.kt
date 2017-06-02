package com.rays.kotlinexample.activity.girl

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.viewimg.ViewImgActivity
import com.rays.kotlinexample.entity.GanHuoData

/**
 * Created by Rays on 2017/6/1.
 */
class GirlListAdapter : BaseQuickAdapter<GanHuoData, BaseViewHolder>(R.layout.item_girl) {

    override fun convert(holder: BaseViewHolder, item: GanHuoData) {
        Glide.with(holder.itemView.context)
                .load(item.url)
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.getView(R.id.iv_img))
        holder.itemView.setOnClickListener {
            ViewImgActivity.start(it.context, it, 0, item.url ?: "")
        }
    }

}