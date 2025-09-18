package com.example.autohub.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Конвертирует объект [Date] в строку формата "dd/MM/yyyy HH:mm".
 *
 * @receiver Дата для форматирования
 * @return Строковое представление даты
 */
fun Date.toFullString(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Europe/Moscow")
    return sdf.format(this)
}

/**
 * Получает текущий системный час и минуты в виде строки "HH:mm".
 *
 * @return Строковое представление текущего времени
 */
fun getTimeString(): String {
    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Europe/Moscow")
    }.format(Date())

    return time
}

/**
 * Конвертирует строку формата "HH:mm" в миллисекунды.
 *
 * @receiver Время в строковом формате
 * @return Время в миллисекундах с начала эпохи
 */
fun String.toMillis(): Long {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("Europe/Moscow")
    }

    val date = timeFormat.parse(this)

    return date?.time ?: 0L
}