package com.rays.kotlinexample.activity.daily

import android.text.SpannableStringBuilder
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.WebViewActivity
import com.rays.kotlinexample.entity.DailyItemType
import com.rays.kotlinexample.entity.DailyTitle
import com.rays.kotlinexample.entity.GanHuoData
import com.rays.kotlinexample.util.DateUtils
import com.rays.kotlinexample.util.SpannableStringUtils

/**
 * Created by Rays on 2017/5/31.
 */
class DailyAdapter(data: List<MultiItemEntity>?) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(DailyItemType.TITLE.ordinal, R.layout.item_daily_item_title)
        addItemType(DailyItemType.CONTENT.ordinal, R.layout.item_daily_item)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (item) {
            is DailyTitle -> {
                holder.getView<TextView>(R.id.tv_title).text = item.title
            }
            is GanHuoData -> {
                val builder = SpannableStringBuilder()
                println(item.desc)
                with(builder) {
                    append(SpannableStringUtils.format(holder.itemView.context,
                            "[${DateUtils.friendlyTime(item.publishedAt?.replace('T', ' ')?.replace('Z', ' ') ?: "")}]",
                            R.style.ByTextAppearance))
                    append(item.desc)
                    append(SpannableStringUtils.format(holder.itemView.context, " [via ${item.who}]", R.style.ByTextAppearance))
                    holder.getView<TextView>(R.id.tv_title).text = subSequence(0, builder.length)
                }
                holder.itemView.setOnClickListener {
                    WebViewActivity.start(holder.itemView.context, item.desc ?: "", item.url ?: "")
                }
                holder.itemView.setOnLongClickListener {
                    //                    DialogUtils.showActionDialog(v.getContext(), v
//                            , new CollectTable(recently.getDesc(), recently.getUrl(), recently.getType()));
                    true
                }
            }
        }
    }
}