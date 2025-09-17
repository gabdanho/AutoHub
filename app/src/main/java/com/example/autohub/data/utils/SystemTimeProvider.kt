package com.example.autohub.data.utils

import com.example.autohub.domain.utils.TimeProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SystemTimeProvider : TimeProvider {

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    private val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun millisToDate(millis: Long): String = dateFormat.format(Date(millis))

    override fun millisToTime(millis: Long): String = timeFormat.format(Date(millis))

    companion object {
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val TIME_FORMAT = "HH:mm"
    }
}