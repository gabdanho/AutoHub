package com.example.autohub.data.local.model

import com.example.autohub.domain.model.result.DbResult

/**
 * Выполняет безопасный вызов операции с базой данных Room.
 *
 * Блок [block] оборачивается в `try/catch`: при успешном выполнении возвращает результат,
 * при возникновении любого исключения логирует сообщение об ошибке с уровнем DEBUG и возвращает `null`.
 *
 * @param block Лямбда с suspend-функцией, выполняющей операцию чтения/записи в Room и возвращающей значение типа `T?`.
 * @return Результат выполнения [block] или `null`, если во время выполнения произошло исключение.
 */
suspend fun <T> safeDbCall(block: suspend () -> T): DbResult<T> {
    return try {
        DbResult.Success(block())
    } catch (e: Exception) {
        DbResult.Error(message = e.message ?: "Unknown error")
    }
}