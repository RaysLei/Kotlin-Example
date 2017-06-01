package com.rays.kotlinexample.activity.girl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.ToolbarActivity
import com.rays.kotlinexample.network.service.DefaultService
import com.rays.kotlinexample.network.service.ServiceFactory
import common.framework.util.PageUtil
import common.framework.util.RxUtils
import kotlinx.android.synthetic.main.activity_girl.*

/**
 * Created by Rays on 2017/6/1.
 */
class GirlActivity : ToolbarActivity() {
    lateinit var adapter: GirlListAdapter
    val pageUtil = PageUtil(10)

    companion object {
        val EXTRA_TITLE = "title"
        fun start(context: Context, title: String) {
            val intent = Intent(context, GirlActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_girl)
        initAdapter()
        addListener()
        loadData(pageUtil.firstPage)
    }

    private fun initAdapter() {
        adapter = GirlListAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun addListener() {
        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            loadData(pageUtil.firstPage)
        }
        adapter.setOnLoadMoreListener({
            swipeRefreshLayout.isEnabled = false
            loadData(pageUtil.nextPage)
        }, recyclerView)
    }

    override fun getToolbarTitle() = intent.getStringExtra(EXTRA_TITLE) ?: ""

    private fun loadData(page: Int) {
        ServiceFactory.instance
                .createService(DefaultService::class.java, DefaultService.baseUrl)
                .getGanHuo("福利", page)
                .compose(bindToLifecycle())
                .compose(RxUtils.defaultSchedulers())
                .subscribe({
                    if (it.error == false) {
                        pageUtil.skipSuccess()
                        if (pageUtil.isFirstPage) {
                            adapter.setNewData(it.results)
                        } else {
                            adapter.addData(it.results!!)
                        }
                        if (it.results!!.size < pageUtil.pageSize) {
                            adapter.loadMoreEnd()
                        } else {
                            adapter.loadMoreComplete()
                        }
                    }
                }, {
                    it.printStackTrace()
                }, {
                    swipeRefreshLayout.isRefreshing = false
                    swipeRefreshLayout.isEnabled = true
                    adapter.setEnableLoadMore(true)
                }, {
                    if (pageUtil.isFirstPage && !swipeRefreshLayout.isRefreshing)
                        swipeRefreshLayout.isRefreshing = true
                })
    }
}