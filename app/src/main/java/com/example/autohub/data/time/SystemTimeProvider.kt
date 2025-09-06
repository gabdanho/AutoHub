package com.example.autohub.data.time

import com.example.autohub.domain.utils.TimeProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SystemTimeProvider : TimeProvider {

    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    override fun millisToDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date(millis))
    }
}