package com.rays.kotlinexample.activity.main

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rays.kotlinexample.R
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
                .into(ivImg)

        holder.itemView.setOnClickListener {
            if (item.name == "福利") {
//                SubActivity.start(v.getContext(), "看妹纸", SubActivity.TYPE_MEIZHI)
            } else {
//                CategoryActivity.start(v.getContext(), type);
            }
        }
    }

}