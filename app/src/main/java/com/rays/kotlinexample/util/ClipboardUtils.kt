package com.rays.kotlinexample.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build

/**
 * Created by _SOLID
 * Date:2016/4/22
 * Time:11:45
 */
object ClipboardUtils {

    private val isNew: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB

    private fun instance(context: Context): ClipboardManager {
        if (isNew) {
            return context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        } else {
            return context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        }
    }

    /**
     * 为剪切板设置内容

     * @param context
     * *
     * @param text
     */
    fun setText(context: Context, text: CharSequence) {
        if (isNew) {

            // Creates a new text clip to put on the clipboard
            val clip = ClipData.newPlainText("simple text", text)

            // Set the clipboard's primary clip.
            instance(context).primaryClip = clip
        } else {
            instance(context).text = text
        }
    }

    /**
     * 获取剪切板的内容

     * @param context
     * *
     * @return
     */
    fun getText(context: Context): CharSequence {
        val sb = StringBuilder()
        val clipboardManager = instance(context)
        if (isNew) {
            if (!clipboardManager.hasPrimaryClip()) {
                return sb.toString()
            } else {
                val clipData = clipboardManager.primaryClip
                val count = clipData.itemCount

                for (i in 0..count - 1) {

                    val item = clipData.getItemAt(i)
                    val str = item.coerceToText(context)
                    sb.append(str)
                }
            }

        } else {
            sb.append(clipboardManager.text)
        }
        return sb.toString()
    }
}
