package com.rays.kotlinexample.activity.viewimg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_view_img.*

/**
 * Created by Rays on 2017/6/1.
 */
class ViewImgActivity : BaseActivity() {

    lateinit var imgArrays: Array<String>
    var curIndex: Int = 0

    companion object {
        val EXTRA_URLS = "urls"
        val EXTRA_INDEX = "index"
        fun start(context: Context, v: View, index: Int? = 0, vararg urls: String) {
            val intent = Intent(context, ViewImgActivity::class.java)
            intent.putExtra(EXTRA_URLS, urls)
            intent.putExtra(EXTRA_INDEX, index)
            val compat = ActivityOptionsCompat.makeScaleUpAnimation(v,
                    v.width / 2, v.height / 2, 0, 0)
            ActivityCompat.startActivity(context, intent,
                    compat.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_view_img)
        initData()
    }

    private fun initData() {
        imgArrays = intent.getStringArrayExtra(EXTRA_URLS)
        curIndex = intent.getIntExtra(EXTRA_INDEX, 0)
        setCurrentIndex(curIndex)

        viewpager.adapter = ImagePagerAdapter(imgArrays)
        viewpager.setCurrentItem(curIndex, false)
    }

    fun setCurrentIndex(index: Int) {
        tv_index.text = "${index + 1}/${imgArrays.size}"
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
        overridePendingTransition(0, R.anim.activity_close)
    }
}