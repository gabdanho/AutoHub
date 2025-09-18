package com.example.autohub.domain.model.result

/**
 * Теги ошибок для использования с [HandledException].
 */
enum class HandleErrorTag {
    /** Ошибка при отсутствии пользователя. */
    USER_NULL,
    /** Ошибка при отсутствии интернет-соединения. */
    NO_INTERNET
}