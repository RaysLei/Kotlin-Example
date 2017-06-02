package com.rays.kotlinexample.util

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by _SOLID
 * Date:2016/5/9
 * Time:11:30
 */
class SnackBarUtils private constructor(private val mSnackBar: Snackbar) {

    private fun getSnackBarLayout(snackbar: Snackbar?): View? = snackbar?.view

    private fun setSnackBarBackColor(colorId: Int): Snackbar {
        val snackBarView = getSnackBarLayout(mSnackBar)
        snackBarView?.setBackgroundColor(colorId)
        return mSnackBar
    }

    fun info() {
        setSnackBarBackColor(color_info)
        show()
    }

    fun info(actionText: String, listener: View.OnClickListener) {
        setSnackBarBackColor(color_info)
        show(actionText, listener)
    }

    fun warning() {
        setSnackBarBackColor(color_warning)
        show()
    }

    fun warning(actionText: String, listener: View.OnClickListener) {
        setSnackBarBackColor(color_warning)
        show(actionText, listener)
    }

    fun danger() {
        setSnackBarBackColor(color_danger)
        show()
    }

    fun danger(actionText: String, listener: View.OnClickListener) {
        setSnackBarBackColor(color_danger)
        show(actionText, listener)
    }

    fun success() {
        setSnackBarBackColor(color_success)
        show()
    }

    fun success(actionText: String, listener: View.OnClickListener) {
        setSnackBarBackColor(color_success)
        show(actionText, listener)
    }

    fun show() {
        mSnackBar.show()
    }

    fun show(actionText: String, listener: View.OnClickListener) {
        mSnackBar.setActionTextColor(color_action)
        mSnackBar.setAction(actionText, listener).show()
    }

    companion object {
        private val color_danger = 0XFFA94442.toInt()
        private val color_success = 0XFF00CD00.toInt()
        private val color_info = 0XFF31708F.toInt()
        private val color_warning = 0XFFFF8247.toInt()

        private val color_action = 0XFFCDC5BF.toInt()

        fun makeShort(view: View, text: String): SnackBarUtils {
            val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            return SnackBarUtils(snackbar)
        }

        fun makeLong(view: View, text: String): SnackBarUtils {
            val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            return SnackBarUtils(snackbar)
        }
    }
}
