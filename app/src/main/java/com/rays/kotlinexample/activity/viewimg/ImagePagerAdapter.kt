package com.rays.kotlinexample.activity.viewimg

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.chrisbanes.photoview.PhotoView
import com.rays.kotlinexample.R

/**
 * Created by Rays on 2017/6/1.
 */
class ImagePagerAdapter(val arrays: Array<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(container.context)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        photoView.layoutParams = layoutParams
        Glide.with(container.context)
                .load(arrays[position])
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(photoView)
        container.addView(photoView)
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = arrays.size
}