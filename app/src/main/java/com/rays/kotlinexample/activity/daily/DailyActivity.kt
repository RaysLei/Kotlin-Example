package com.rays.kotlinexample.activity.daily

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextPaint
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.BaseActivity
import com.rays.kotlinexample.entity.DailyTitle
import com.rays.kotlinexample.network.service.DefaultService
import com.rays.kotlinexample.network.service.ServiceFactory
import common.framework.util.RxUtils
import kotlinx.android.synthetic.main.activity_daily.*
import org.jetbrains.anko.toast

/**
 * Created by Rays on 2017/5/31.
 */
class DailyActivity : BaseActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var adapter: DailyAdapter

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_DATE = "date"
        const val EXTRA_IMAGE_URL = "imageUrl"

        fun start(activity: Activity, title: String, date: String, imageUrl: String, v: View? = null) {
            val intent = Intent(activity, DailyActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_DATE, date)
            intent.putExtra(EXTRA_IMAGE_URL, imageUrl)
            if (v == null) {
                activity.startActivity(intent)
            } else {
                val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        v,
                        activity.resources.getString(R.string.image_transition))
                ActivityCompat.startActivity(activity, intent, compat.toBundle())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_daily)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar_layout.title = intent.getStringExtra(EXTRA_TITLE)?.replace("今日力推：", "") ?: ""
        boldTitleText(collapsing_toolbar_layout)

        Glide.with(this)
                .load(intent.getStringExtra(EXTRA_IMAGE_URL))
                .placeholder(R.color.md_grey_300)
                .error(R.color.md_red_300)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv_image)
        iv_image.setOnClickListener { toast("Image") }

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DailyAdapter(null)
        recyclerView.adapter = adapter

        progressDialog = ProgressDialog(this)

        loadData()
    }

    /**
     * 加粗CollapsingToolbarLayout的标题文字

     * @param collapsing_toolbar CollapsingToolbarLayout
     */
    private fun boldTitleText(collapsing_toolbar: CollapsingToolbarLayout) {
        try {
            var clazz: Class<*> = collapsing_toolbar.javaClass
            var field = clazz.getDeclaredField("mCollapsingTextHelper")
            field.isAccessible = true
            //二次反射
            val textHelper = field.get(collapsing_toolbar)
            clazz = textHelper.javaClass
            field = clazz.getDeclaredField("mTextPaint")
            field.isAccessible = true
            val textPaint = field.get(textHelper) as TextPaint
            textPaint.isFakeBoldText = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadData() {
        val date = intent.getStringExtra(EXTRA_DATE)
        val newDate = if (date.contains("-")) date.replace("-", "/") else date

        ServiceFactory.instance
                .createService(DefaultService::class.java, DefaultService.baseUrl)
                .getRecentlyByDate(newDate)
                .compose(bindToLifecycle())
                .compose(RxUtils.defaultSchedulers())
                .subscribe({
                    if (it.error == false) {
                        val list = ArrayList<MultiItemEntity>()
                        it.results?.let {
                            it.休息视频?.let {
                                list.add(DailyTitle("休息视频"))
                                list.addAll(it)
                            }
                            it.Android?.let {
                                list.add(DailyTitle("Android"))
                                list.addAll(it)
                            }
                            it.iOS?.let {
                                list.add(DailyTitle("iOS"))
                                list.addAll(it)
                            }
                            it.前端?.let {
                                list.add(DailyTitle("前端"))
                                list.addAll(it)
                            }
                            it.App?.let {
                                list.add(DailyTitle("App"))
                                list.addAll(it)
                            }
                            it.瞎推荐?.let {
                                list.add(DailyTitle("瞎推荐"))
                                list.addAll(it)
                            }
                        }
                        adapter.setNewData(list)
                    }
                }, {
                    it.printStackTrace()
                }, {
                    progressDialog.dismiss()
                }, {
                    progressDialog.show()
                })
    }
}