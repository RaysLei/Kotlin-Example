package com.rays.kotlinexample.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.view.menu.MenuBuilder
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.rays.kotlinexample.R
import com.rays.kotlinexample.util.ClipboardUtils
import com.rays.kotlinexample.util.SystemShareUtils
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
            setText(getExtraTitle())
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

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressbar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressbar.visibility = View.GONE
            }
        })
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                progressbar.progress = newProgress
//                if (newProgress == 0) {
////                    loadStart()
//                } else if (newProgress > 90) {
//                    progressbar.visibility = View.GONE
//                } else {
//                    progressbar.visibility = View.VISIBLE
//                }
            }
        })
    }

    fun initData() {
        webView.loadUrl(getExtraUrl())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_webview_toolbar, menu)
        return true
    }

    override fun onPrepareOptionsPanel(view: View?, menu: Menu?): Boolean {
        menu?.let {
            if (it is MenuBuilder) it.setOptionalIconsVisible(true)
        }
        return super.onPrepareOptionsPanel(view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                SystemShareUtils.shareText(this, "【${getExtraTitle()}】${getExtraUrl()}")
            }
            R.id.action_copy -> {
                ClipboardUtils.setText(this, getExtraUrl())
                Snackbar.make(toolbar, R.string.clipboard_hint, Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_browser -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getExtraUrl())))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getExtraUrl() = intent.getStringExtra(EXTRA_URL)

    private fun getExtraTitle() = intent.getStringExtra(EXTRA_TITLE)

    override fun onBackPressed() {
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