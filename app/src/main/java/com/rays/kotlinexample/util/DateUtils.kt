package com.rays.kotlinexample.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by _SOLID
 * Date:2016/5/10
 * Time:9:58
 */
object DateUtils {
    var formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var formatDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日
     */
    fun formatDate(date: Date): String {
        return formatDate.format(date)
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日 时分秒
     */
    fun formatDateTime(date: Date): String {
        return formatDateTime.format(date)
    }

    /**
     * 判断是不是今天
     *
     * @param date
     * @return
     */
    fun isToday(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = date
        return year == calendar.get(Calendar.YEAR)
                && month == calendar.get(Calendar.MONTH)
                && day == calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 将时间戳解析成日期
     *
     * @param timeInMillis
     * @return 年-月-日
     */
    fun parseDate(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val date = calendar.time
        return formatDate(date)
    }

    /**
     * 将时间戳解析成日期
     *
     * @param timeInMillis
     * @return 年-月-日 时:分:秒
     */
    fun parseDateTime(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val date = calendar.time
        return formatDateTime(date)
    }

    /**
     * 解析日期
     *
     * @param date 年-月-日
     * @return
     */
    fun parseDate(date: String): Date? {
        var mDate: Date? = null
        try {
            mDate = formatDate.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return mDate
    }

    /**
     * 解析日期
     *
     * @param datetime
     * @return
     */
    fun parseDateTime(datetime: String): Date? {
        var mDate: Date? = null
        try {
            mDate = formatDateTime.parse(datetime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return mDate
    }


    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    fun friendlyTime(sdate: String): String {
        val time = parseDateTime(sdate) ?: return "Unknown"
        var ftime = ""
        val cal = Calendar.getInstance()

        // 判断是否是同一天
        val curDate = formatDate.format(cal.time)
        val paramDate = formatDate.format(time)
        if (curDate == paramDate) {
            val hour = ((cal.timeInMillis - time.time) / 3600000).toInt()
            if (hour == 0)
                ftime = Math.max(
                        (cal.timeInMillis - time.time) / 60000, 1).toString() + "分钟前"
            else
                ftime = hour.toString() + "小时前"
            return ftime
        }

        val lt = time.time / 86400000
        val ct = cal.timeInMillis / 86400000
        val days = (ct - lt).toInt()
        if (days == 0) {
            val hour = ((cal.timeInMillis - time.time) / 3600000).toInt()
            if (hour == 0)
                ftime = Math.max(
                        (cal.timeInMillis - time.time) / 60000, 1).toString() + "分钟前"
            else
                ftime = hour.toString() + "小时前"
        } else if (days == 1) {
            ftime = "昨天"
        } else if (days == 2) {
            ftime = "前天"
        } else if (days in 3..10) {
            ftime = days.toString() + "天前"
        } else if (days > 10) {
            ftime = formatDate.format(time)
        }
        return ftime
    }
}
