package com.rays.kotlinexample.activity.category

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.WebViewActivity
import com.rays.kotlinexample.activity.viewimg.ViewImgActivity
import com.rays.kotlinexample.entity.CategoryItem
import com.rays.kotlinexample.util.DateUtils

/**
 * Created by Rays on 2017/6/2.
 */
class CategoryListAdapter : BaseMultiItemQuickAdapter<CategoryItem, BaseViewHolder>(null) {

    init {
        addItemType(CategoryItem.TYPE_TEXT, R.layout.item_ganhuo_text)
        addItemType(CategoryItem.TYPE_IMG, R.layout.item_ganhuo_image)
    }

    override fun convert(holder: BaseViewHolder, item: CategoryItem) {
        if (item.itemType == CategoryItem.TYPE_TEXT) {
            typeText(holder, item)
        } else {
            typeImage(holder, item)
        }
    }

    private fun typeText(holder: BaseViewHolder, item: CategoryItem) {
        holder.getView<TextView>(R.id.tv_title).text = item.data.desc
        holder.getView<TextView>(R.id.tv_people).text = getPeople(item)
        holder.getView<TextView>(R.id.tv_time).text = getDate(item)
        holder.itemView.setOnClickListener {
            WebViewActivity.start(it.context, item.data.desc ?: "", item.data.url ?: "")
        }
    }

    private fun typeImage(holder: BaseViewHolder, item: CategoryItem) {
        holder.getView<TextView>(R.id.tv_title).text = item.data.desc
        holder.getView<TextView>(R.id.tv_people).text = getPeople(item)
        holder.getView<TextView>(R.id.tv_time).text = getDate(item)
        holder.getView<ViewPager>(R.id.viewpager).adapter = ImgViewPagerAdapter(item.data.images ?: emptyList())
        holder.getView<View>(R.id.rl_bottom).setOnClickListener { WebViewActivity.start(it.context, item.data.desc ?: "", item.data.url ?: "") }
    }

    private fun getPeople(item: CategoryItem) = "via ${item.data.who}"

    private fun getDate(item: CategoryItem): String {
        val date = item.data.publishedAt?.replace('T', ' ')?.replace('Z', ' ') ?: ""
        return DateUtils.friendlyTime(date)
    }

    class ImgViewPagerAdapter(val images: List<String>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(container.context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            Glide.with(container.context)
                    .load(images[position])
                    .placeholder(R.color.md_grey_300)
                    .error(R.color.md_red_300)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView)
            imageView.setOnClickListener { ViewImgActivity.start(it.context, imageView, 0, *images.toTypedArray()) }
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun isViewFromObject(view: View, obj: Any) = view == obj

        override fun getCount() = images.size

    }
}