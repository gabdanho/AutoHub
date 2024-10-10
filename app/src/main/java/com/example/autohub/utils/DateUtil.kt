package com.example.autohub.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
fun getFullDate(): String {
    val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Europe/Moscow")
    }.format(Date())

    return date
}

fun getTimeString(): String {
    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Europe/Moscow")
    }.format(Date())

    return time
}

fun String.toMillis(): Long {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Europe/Moscow")
    }

    val date = timeFormat.parse(this)

    return date?.time ?: 0L
}