package com.kv.atmapplication.util

import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val newdate: Date
        return if (value != null) {
            try {
                newdate = df.parse(value)
                //                Utils.logd(TAG, "fromTimestamp" + newdate + " " + value);
                return newdate
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }


    @TypeConverter
    fun dateToTimestamp(value: Date?): String? {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

//        Utils.logd(TAG, "dateToTimestamp " + df.format(value) + " value " + value);
        //        date = date.replace(",", "");
        return df.format(value)
    }

    fun getTodayDate(dateFormat: DateFormat?): String? {
        var dateFormat = dateFormat
        if (dateFormat == null) {
            dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH) // HH:mm:ss
        }
        val date = Date()
        return dateFormat.format(date)
    }

    fun getYesterdayDate(dateFormat: DateFormat?): String? {
        var dateFormat = dateFormat
        if (dateFormat == null) {
            dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH) // HH:mm:ss
        }
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time)
    }

    fun getOtherDate(days: Int, dateFormat: DateFormat?): String? {
        var dateFormat = dateFormat
        if (dateFormat == null) {
            dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH) // HH:mm:ss
        }
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, days)
        return dateFormat.format(cal.time)
    }
}