package com.example.autohub.domain.model.result

/**
 * Исключение, которое было обработано с использованием тега [HandleErrorTag].
 *
 * @property tag Тип обработанной ошибки.
 */
class HandledException(val tag: HandleErrorTag) : Exception()