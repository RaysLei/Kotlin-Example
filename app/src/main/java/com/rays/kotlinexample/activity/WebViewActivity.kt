package com.rays.kotlinexample.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.rays.kotlinexample.R
import com.rays.kotlinexample.util.WebViewUtil
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Rays on 2017/5/31.
 */
class WebViewActivity : BaseActivity() {

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_URL = "url"
        fun start(context: Context, title: String, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_web_view)
        initToolbar()
        initWebView()
        initData()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)//决定左上角的图标是否可以点击
            it.setDisplayHomeAsUpEnabled(true)//决定左上角图标的右侧是否有向左的小箭头
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { finish() }

        with(textSwitcher) {
            setFactory {
                val textView = TextView(this@WebViewActivity)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextAppearance(R.style.WebTitle)
                } else {
                    textView.setTextAppearance(this@WebViewActivity, R.style.WebTitle)
                }
                textView.setSingleLine(true)
                textView.ellipsize = TextUtils.TruncateAt.MARQUEE
                textView.setOnClickListener { v -> v.isSelected = !v.isSelected }
                textView
            }
            setInAnimation(this@WebViewActivity, android.R.anim.fade_in)
            setOutAnimation(this@WebViewActivity, android.R.anim.fade_out)
            setText(intent.getStringExtra(EXTRA_TITLE))
            isSelected = true
        }
    }

    fun initWebView() {
        WebViewUtil.setWebViewOptions(webView)
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.url.toString())
                }
                return true
            }
        })
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 0) {
//                    loadStart()
                } else if (newProgress > 90) {
                    progressbar.visibility = View.GONE
                } else {
                    progressbar.visibility = View.VISIBLE
                }
            }
        })
    }

    fun initData() {
        webView.loadUrl(intent.getStringExtra(EXTRA_URL))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}