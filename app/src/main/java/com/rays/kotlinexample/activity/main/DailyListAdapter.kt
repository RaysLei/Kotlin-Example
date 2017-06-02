package com.rays.kotlinexample.activity.main

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.daily.DailyActivity
import com.rays.kotlinexample.entity.Daily
import com.rays.kotlinexample.util.DateUtils
import java.util.*

/**
 * Created by Rays on 2017/5/27.
 */
class DailyListAdapter : BaseQuickAdapter<Daily, BaseViewHolder>(R.layout.item_daily) {

    override fun convert(holder: BaseViewHolder, item: Daily) {
        val ivImg = holder.getView<ImageView>(R.id.iv_img)
        Glide.with(ivImg.context)
                .load(item.imgUrl)
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(ivImg)
        ivImg.setColorFilter(Color.parseColor("#5e000000"))

        val isToday = DateUtils.isToday(DateUtils.parseDate(item.date) ?: Date())
        val tvDate = holder.getView<TextView>(R.id.tv_date)
        tvDate.text = if (isToday) "#今日推荐" else "#${item.date}"

        val tvDesc = holder.getView<TextView>(R.id.tv_desc)
        tvDesc.text = item.title?.replace("今日力推：", "")

        holder.itemView.setOnClickListener {
            DailyActivity.start(it.context as Activity, item.title ?: "", item.date, item.imgUrl, it)
        }
        holder.itemView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        ivImg.colorFilter = null
                        hide(tvDate)
                        hide(tvDesc)
                        val parent = holder.itemView.parent
                        parent?.let {
                            (parent as View).setOnTouchListener(this)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        ivImg.setColorFilter(Color.parseColor("#5e000000"))
                        show(tvDate)
                        show(tvDesc)
                    }
                }
                return false
            }

        })
    }

    private fun show(v: View) {
        val alpha = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f)
        alpha.duration = 300
        alpha.start()
    }

    private fun hide(v: View) {
        val alpha = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f)
        alpha.duration = 300
        alpha.start()
    }
}