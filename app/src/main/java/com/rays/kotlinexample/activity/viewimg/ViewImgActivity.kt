package com.rays.kotlinexample.activity.viewimg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.rays.kotlinexample.R
import com.rays.kotlinexample.activity.BaseActivity
import com.rays.kotlinexample.network.service.DefaultService
import com.rays.kotlinexample.network.service.ServiceFactory
import com.rays.kotlinexample.util.FileUtils
import com.rays.kotlinexample.util.SnackBarUtils
import common.framework.util.RxUtils
import kotlinx.android.synthetic.main.activity_view_img.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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
        addListener()
    }

    private fun initData() {
        imgArrays = intent.getStringArrayExtra(EXTRA_URLS)
        curIndex = intent.getIntExtra(EXTRA_INDEX, 0)
        setCurrentIndex(curIndex)

        viewpager.adapter = ImagePagerAdapter(imgArrays)
        viewpager.setCurrentItem(curIndex, false)
    }

    private fun addListener() {
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                setCurrentIndex(position)
            }

        })
        iv_download.setOnClickListener { download() }
    }

    fun setCurrentIndex(index: Int) {
        curIndex = index
        tv_index.text = "${index + 1}/${imgArrays.size}"
    }

    fun download() {
        val url = imgArrays[curIndex]
        ServiceFactory.instance
                .createService(DefaultService::class.java, DefaultService.baseUrl)
                .download(url)
                .compose(RxUtils.allIoSchedulers())
                .subscribe({
                    var inputStream: InputStream? = null
                    var outStream: FileOutputStream? = null
                    try {
                        val filePath = FileUtils.getSaveImagePath(this@ViewImgActivity) + File.separator + FileUtils.getFileName(url)
                        outStream = FileOutputStream(File(filePath))
                        inputStream = it.byteStream()
                        val bytes = ByteArray(4096)
                        var read: Int
                        while (true) {
                            read = inputStream.read(bytes)
                            if (read == -1) break
                            outStream.write(bytes, 0, read)
                        }
                        outStream.flush()
                        downloadCompleted(true)
                    } catch(e: Exception) {
                        e.printStackTrace()
                        downloadCompleted(false)
                    } finally {
                        outStream?.close()
                        inputStream?.close()
                    }

                })

    }

    private fun downloadCompleted(isSuccess: Boolean) {
        runOnUiThread {
            if (isSuccess) {
                SnackBarUtils.makeLong(viewpager, "已保存至相册").info()
            } else {
                SnackBarUtils.makeLong(viewpager, "保存失败").danger()
            }
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
        overridePendingTransition(0, R.anim.activity_close)
    }
}