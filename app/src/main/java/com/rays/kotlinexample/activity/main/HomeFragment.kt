package com.rays.kotlinexample.activity.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rays.kotlinexample.R
import com.rays.kotlinexample.entity.Daily
import com.rays.kotlinexample.entity.HttpResult
import com.rays.kotlinexample.fragment.BaseFragment
import com.rays.kotlinexample.network.service.DefaultService
import com.rays.kotlinexample.network.service.ServiceFactory
import common.framework.util.PageUtil
import common.framework.util.RxUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Rays on 2017/5/26.
 */
class HomeFragment : BaseFragment() {

    lateinit var adapter: DailyListAdapter
    val pageUtil = PageUtil(10)

    companion object {
        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
        initData()
    }

    private fun addListener() {
        swipeRefreshLayout.setOnRefreshListener {
            adapter.setEnableLoadMore(false)
            loadData(pageUtil.firstPage)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

            }
        })
    }

    private fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = DailyListAdapter()
        adapter.setOnLoadMoreListener({
            swipeRefreshLayout.isEnabled = false
            loadData(pageUtil.nextPage)
        }, recyclerView)
        recyclerView.adapter = adapter

        val categoryRecyclerView = RecyclerView(context)
        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = CategoryAdapter()
        adapter.addHeaderView(categoryRecyclerView)

        loadData(pageUtil.firstPage)
    }

    private fun loadData(page: Int) {
        ServiceFactory.instance
                .createService(DefaultService::class.java, DefaultService.baseUrl)
                .getRecentlyList(page)
                .compose(bindToLifecycle())
                .compose(RxUtils.defaultSchedulers())
                .subscribe(object : Observer<HttpResult<List<Daily>>> {
                    override fun onNext(result: HttpResult<List<Daily>>) {
                        println("onNext error: ${result.error} results: ${result.results?.size}")
                        if (result.error == false) {
                            pageUtil.skipSuccess()
                            if (pageUtil.isFirstPage) {
                                adapter.setNewData(result.results)
                            } else {
                                adapter.addData(result.results as List<Daily>)
                            }
                            if (result.results?.size!! < pageUtil.pageSize) {
                                adapter.loadMoreEnd()
                            } else {
                                adapter.loadMoreComplete()
                            }
                        }
                    }

                    override fun onSubscribe(disposable: Disposable) {
                        println("onSubscribe $disposable")
                    }

                    override fun onComplete() {
                        println("onComplete")
                        swipeRefreshLayout.isRefreshing = false
                        swipeRefreshLayout.isEnabled = true
                        adapter.setEnableLoadMore(true)
                    }

                    override fun onError(e: Throwable) {
                        println("onError")
                        e.printStackTrace()
                    }
                })
    }
}