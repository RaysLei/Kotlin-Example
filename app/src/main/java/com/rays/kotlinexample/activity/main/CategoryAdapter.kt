package com.rays.kotlinexample.activity.main

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.category.CategoryListActivity
import com.rays.kotlinexample.activity.girl.GirlActivity
import com.rays.kotlinexample.constant.Category
import com.rays.kotlinexample.entity.CategoryEntity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by Rays on 2017/5/31.
 */
class CategoryAdapter : BaseQuickAdapter<CategoryEntity, BaseViewHolder>(R.layout.item_category, Category.getGanHuoCategory()) {

    override fun convert(holder: BaseViewHolder, item: CategoryEntity) {
        holder.getView<TextView>(R.id.tv_title).text = item.name

        val ivImg = holder.getView<ImageView>(R.id.iv_img)
        Glide.with(ivImg.context)
                .load(item.imgUrl)
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .bitmapTransform(RoundedCornersTransformation(ivImg.context, ivImg.context.resources.getDimensionPixelSize(R.dimen.img_corner_radius), 0))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivImg)

        holder.itemView.setOnClickListener {
            if (item.name == "福利") {
                GirlActivity.start(it.context, "看妹纸")
            } else {
                CategoryListActivity.start(it.context, item.name)
            }
        }
    }

}