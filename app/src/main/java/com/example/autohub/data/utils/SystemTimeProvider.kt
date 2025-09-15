package com.example.autohub.data.utils

import com.example.autohub.domain.utils.TimeProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SystemTimeProvider : TimeProvider {

    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun millisToDate(millis: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(Date(millis))
    }

    override fun millisToTime(millis: Long): String {
        val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return timeFormat.format(Date(millis))
    }

    companion object {
        const val DATE_FORMAT = "dd.MM.yyyy"
        const val TIME_FORMAT = "HH:mm"
    }
}