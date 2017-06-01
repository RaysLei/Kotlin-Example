package com.rays.kotlinexample.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.rays.kotlinexample.R

import java.util.ArrayList

/**
 * Created by _SOLID
 * Date:2016/4/22
 * Time:12:45
 */
object SystemShareUtils {

    fun shareText(ctx: Context, text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        ctx.startActivity(Intent.createChooser(sendIntent, ctx.getString(R.string.share_to)))
    }

    fun shareImage(ctx: Context, uri: Uri) {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sendIntent.type = "image/jpeg"
        ctx.startActivity(Intent.createChooser(sendIntent, ctx.getString(R.string.share_to)))
    }

    fun shareImageList(ctx: Context, uris: ArrayList<Uri>) {
        val sendIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        sendIntent.putExtra(Intent.EXTRA_STREAM, uris)
        sendIntent.type = "image/*"
        ctx.startActivity(Intent.createChooser(sendIntent, ctx.getString(R.string.share_to)))
    }
}
