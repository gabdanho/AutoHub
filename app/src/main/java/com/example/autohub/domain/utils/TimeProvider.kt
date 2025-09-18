package com.example.autohub.domain.utils

/**
 * Интерфейс для получения текущего времени и конвертации времени в формат даты и времени.
 */
interface TimeProvider {

    /**
     * Возвращает текущее время в миллисекундах.
     *
     * @return Текущее время в миллисекундах.
     */
    fun currentTimeMillis(): Long

    /**
     * Преобразует миллисекунды в строку с датой.
     *
     * @param millis Время в миллисекундах.
     * @return Дата в виде строки.
     */
    fun millisToDate(millis: Long): String

    /**
     * Преобразует миллисекунды в строку с временем.
     *
     * @param millis Время в миллисекундах.
     * @return Время в виде строки.
     */
    fun millisToTime(millis: Long): String
}