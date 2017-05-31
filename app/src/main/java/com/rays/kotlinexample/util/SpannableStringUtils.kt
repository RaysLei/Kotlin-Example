package com.rays.kotlinexample.util

import android.content.Context
import android.text.SpannableString
import android.text.style.TextAppearanceSpan

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:17:10
 */
object SpannableStringUtils {
    fun format(context: Context, text: String, style: Int): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(TextAppearanceSpan(context, style), 0, text.length,
                0)
        return spannableString
    }
}
