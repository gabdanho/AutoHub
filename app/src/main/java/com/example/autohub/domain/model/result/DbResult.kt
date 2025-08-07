package com.example.autohub.domain.model.result

/**
 * Обобщённый класс-обёртка, представляющий результат выполнения DB операций.
 *
 * Может содержать успешный результат или один из возможных типов ошибок.
 *
 * @param T тип данных в случае успешного выполнения.
 */
sealed class DbResult<out T> {

    /**
     * Представляет успешный результат операции.
     *
     * @param T тип возвращаемых данных.
     * @property data данные, полученные в результате успешной операции.
     */
    data class Success<out T>(val data: T) : DbResult<T>()

    /**
     * Ошибка, возникшая на стороне базе данных.
     *
     * @property message сообщение об ошибке.
     */
    data class Error(val message: String) : DbResult<Nothing>()
}
