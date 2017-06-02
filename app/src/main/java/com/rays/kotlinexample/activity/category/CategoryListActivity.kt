package com.rays.kotlinexample.activity.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.ToolbarActivity
import com.rays.kotlinexample.entity.CategoryItem
import com.rays.kotlinexample.network.service.DefaultService
import com.rays.kotlinexample.network.service.ServiceFactory
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import common.framework.util.PageUtil
import common.framework.util.RxUtils
import kotlinx.android.synthetic.main.activity_girl.*

/**
 * Created by Rays on 2017/6/2.
 */
class CategoryListActivity : ToolbarActivity() {

    lateinit var adapter: CategoryListAdapter
    val pageUtil = PageUtil(10)

    companion object {
        val EXTRA_TYPE = "type"
        fun start(context: Context, type: String) {
            val intent = Intent(context, CategoryListActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_girl)
        initData()
        addListener()
        loadData(pageUtil.firstPage)
    }

    private fun initData() {
        adapter = CategoryListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.list_divider_color)
                .sizeResId(R.dimen.list_divider_height)
                .build())
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

    private fun loadData(page: Int) {
        ServiceFactory.instance
                .createService(DefaultService::class.java, DefaultService.baseUrl)
                .getGanHuo(getToolbarTitle(), page)
                .compose(bindToLifecycle())
                .compose(RxUtils.defaultSchedulers())
                .subscribe({
                    if (it.error == false) {
                        val list = it.results?.map {
                            if (it.images?.isNotEmpty() ?: false) {
                                CategoryItem(it, CategoryItem.TYPE_IMG)
                            } else {
                                CategoryItem(it, CategoryItem.TYPE_TEXT)
                            }
                        } ?: emptyList()
                        pageUtil.skipSuccess()
                        if (pageUtil.isFirstPage) {
                            adapter.setNewData(list)
                        } else {
                            adapter.addData(list)
                        }
                        if (list.size < pageUtil.pageSize) {
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
                })
    }

    override fun getToolbarTitle(): String = intent.getStringExtra(EXTRA_TYPE)
}