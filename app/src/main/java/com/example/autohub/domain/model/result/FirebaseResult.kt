package com.example.autohub.domain.model.result

/**
 * Обобщённый класс-обёртка, представляющий результат выполнения операции.
 *
 * Может содержать успешный результат или один из возможных типов ошибок.
 *
 * @param T тип данных в случае успешного выполнения.
 */
sealed class FirebaseResult<out T> {

    /**
     * Представляет успешный результат операции.
     *
     * @param T тип возвращаемых данных.
     * @property data данные, полученные в результате успешной операции.
     */
    data class Success<out T>(val data: T) : FirebaseResult<T>()

    /**
     * Представляет изолированный класс с различными типами ошибок
     */
    sealed class Error(val message: String) : FirebaseResult<Nothing>() {
        /**
         * Ошибка, возникшая на стороне сервера (например, 5xx или 4xx HTTP-коды).
         *
         * @property serverError сообщение об ошибке.
         * @property errorCode необязательный код ошибки (например, HTTP-статус).
         */
        data class ServerError(
            val serverError: String,
            val errorCode: Int? = null
        ) : Error(serverError)

        /**
         * Ошибка, связанная с превышением времени ожидания (тайм-аут).
         *
         * @property timeoutError сообщение об ошибке.
         */
        data class TimeoutError(val timeoutError: String) : Error(timeoutError)

        /**
         * Ошибка подключения к сети или удалённому серверу.
         *
         * @property connectionMessage сообщение об ошибке.
         */
        data class ConnectionError(val connectionMessage: String) : Error(connectionMessage)

        /**
         * Неизвестная ошибка, не подпадающая под другие категории.
         *
         * @property unknownMessage сообщение об ошибке.
         */
        data class UnknownError(val unknownMessage: String) : Error(unknownMessage)
    }
}