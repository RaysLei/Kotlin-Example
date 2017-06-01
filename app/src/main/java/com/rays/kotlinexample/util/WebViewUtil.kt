package com.rays.kotlinexample.util

import android.content.Intent
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by _SOLID
 * Date:2016/12/12
 * Time:17:25
 * Desc:
 */

object WebViewUtil {
    fun setWebViewOptions(webView: WebView) {
        val context = webView.context.applicationContext
        //设置编码
        webView.settings.defaultTextEncodingName = "UTF-8"

        //设置缓存
        webView.settings.domStorageEnabled = true //开启DOM storage API 功能
        webView.settings.databaseEnabled = true //开启database storage API 功能
        webView.settings.setAppCacheEnabled(true)
        webView.settings.setGeolocationEnabled(true)
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptEnabled = true
        //设置自适应屏幕，两者合用
        webView.settings.useWideViewPort = true  //将图片调整到适合webview的大小
        webView.settings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //设置WebView视图大小与HTML中viewport Tag的关系
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        //设置支持缩放
        webView.settings.builtInZoomControls = true
        webView.settings.setSupportZoom(true)
        webView.settings.displayZoomControls = false
        val cacheFile = context.cacheDir
        if (cacheFile != null) {
            webView.settings.setAppCachePath(cacheFile.absolutePath)
        }
        webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        settWebViewDownloadListener(webView)
    }

    private fun settWebViewDownloadListener(webView: WebView) {
        webView.setDownloadListener { url, _, _, _, _ ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            webView.context.startActivity(intent)
        }
    }
}
