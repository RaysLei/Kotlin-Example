package com.rays.kotlinexample.activity

import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.rays.kotlinexample.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Rays on 2017/6/1.
 */
abstract class ToolbarActivity : BaseActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initToolbar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initToolbar()
    }

    protected fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)//决定左上角的图标是否可以点击
            it.setDisplayHomeAsUpEnabled(true)//决定左上角图标的右侧是否有向左的小箭头
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { finish() }

        with(textSwitcher) {
            val context = this@ToolbarActivity
            setFactory {
                val textView = TextView(context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextAppearance(R.style.WebTitle)
                } else {
                    @Suppress("DEPRECATION")
                    textView.setTextAppearance(context, R.style.WebTitle)
                }
                textView.setSingleLine(true)
                textView.ellipsize = TextUtils.TruncateAt.MARQUEE
                textView.setOnClickListener { v -> v.isSelected = !v.isSelected }
                textView
            }
            setInAnimation(context, android.R.anim.fade_in)
            setOutAnimation(context, android.R.anim.fade_out)
            setText(getToolbarTitle())
            isSelected = true
        }
    }

    abstract fun getToolbarTitle(): String
}