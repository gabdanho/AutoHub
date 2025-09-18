package com.example.autohub.data.utils

import com.example.autohub.domain.utils.TimeProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Провайдер текущего системного времени и форматированных дат/времени.
 *
 * Реализация интерфейса [TimeProvider].
 */
class SystemTimeProvider : TimeProvider {

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    private val timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    /** Возвращает текущее время в миллисекундах. */
    override fun currentTimeMillis(): Long = System.currentTimeMillis()

    /**
     * Преобразует миллисекунды в строку с датой в заданный формат [DATE_FORMAT].
     *
     * @param millis Время в миллисекундах
     * @return Строковое представление даты
     */
    override fun millisToDate(millis: Long): String = dateFormat.format(Date(millis))

    /**
     * Преобразует миллисекунды в строку с временем в заданный формат [TIME_FORMAT].
     *
     * @param millis Время в миллисекундах
     * @return Строковое представление времени
     */
    override fun millisToTime(millis: Long): String = timeFormat.format(Date(millis))

    companion object {
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val TIME_FORMAT = "HH:mm"
    }
}